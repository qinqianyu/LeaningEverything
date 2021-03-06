package team.gjz.database.redis.queue;

import com.alibaba.fastjson.JSON;
import team.gjz.database.redis.pool.RedisPoolUtil4J;
import lombok.Data;
import redis.clients.jedis.Jedis;

import java.util.Set;
import java.util.UUID;

/**
 * @description: redis的zset实现的延时队列
 * @since JDK 1.8
 */
public class RedisDelayingQueue<T> {

    /**
     * @param <T> 消息的类型
     * @description: 封装消息分配消息id
     */
    @Data
    static class TaskItem<T> {
        private String id;
        private T msg;
    }

    private Jedis jedis;
    private String queueKey;

    /**
     * @param queueKey 消息队列的key,类似于kafka的topic
     * @description: 封装消息分配消息id
     */
    public RedisDelayingQueue(String queueKey) {
        this.jedis = RedisPoolUtil4J.getConnection();
        this.queueKey = queueKey;
    }

    /**
     * @param msg     消息
     * @param seconds 延迟的秒数
     * @description: 把消息放入队列
     */
    public void delay(T msg, Integer seconds) {
        TaskItem<T> task = new TaskItem<>();
        //分配唯一的uuid
        task.setId(UUID.randomUUID().toString());
        task.msg = msg;
        //fastjson 序列化
        String jsonString = JSON.toJSONString(task);
        //塞入延时队列，5s后再试
        jedis.zadd(queueKey, System.currentTimeMillis() + seconds * 1000, jsonString);
    }

    /**
     * @description: 从队列中取出消息
     */
    public void loop() {
        while (!Thread.interrupted()) {
            //只取一条
            Set<String> values = jedis.zrangeByScore(queueKey, 0, System.currentTimeMillis(), 0, 1);
            if (values.isEmpty()) {
                try {
                    //歇会继续
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
                continue;
            }
            String value = values.iterator().next();
            //如果删除队列中的这条消息成功，代表抢到了消息
            if (jedis.zrem(queueKey, value) > 0) {
                //fastjson 反序列化
                TaskItem<T> task = JSON.parseObject(value, TaskItem.class);
                handlerMsg(task.getMsg());
            }
        }
    }

    /**
     *  @description: 处理消息逻辑函数
     * @param msg 消息
     */
    public void handlerMsg(T msg) {
        System.out.println(System.currentTimeMillis() + "正在处理消息：" + msg);

    }

    public static void main(String[] args) {

        RedisDelayingQueue<String> queue = new RedisDelayingQueue<>("queue-demo");
        Thread product = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                queue.delay("msg-" + System.currentTimeMillis(), 5);
            }
        }, "product");
        Thread consumer = new Thread(() -> queue.loop(), "consumer");
        product.start();
        consumer.start();
        try {
            product.join();
            Thread.sleep(6000);
            consumer.interrupt();
            consumer.join();
        } catch (InterruptedException e) {
        }
    }
}
