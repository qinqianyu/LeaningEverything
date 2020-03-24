package team.gjz.database.redis.queue;


import team.gjz.database.redis.pool.RedisPoolUtil4J;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * Created with IntelliJ IDEA.
 * Description: Redis PubSub 消费者
 * User: leisurexi
 * Date: 2019-10-12
 * Time: 10:32 下午
 */
public class Consumer {

    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = RedisPoolUtil4J.getConnection();
        Subscriber subscriber = new Subscriber();
        jedis.subscribe(subscriber, "mychannel");
    }

    private static class Subscriber extends JedisPubSub {

        @Override
        public void onMessage(String channel, String message) {
            System.out.println(String.format("接收到消息: channel = %s, message = %s", channel, message));
        }

        @Override
        public void onSubscribe(String channel, int subscribedChannels) {
            System.out.println(String.format("订阅主题成功: channel = %s, subscribedChannels = %s", channel, subscribedChannels));
        }

        @Override
        public void onUnsubscribe(String channel, int subscribedChannels) {
            System.out.println(String.format("取消订阅主题成功: channel = %s, subscribedChannels = %s", channel, subscribedChannels));
        }
    }

}


