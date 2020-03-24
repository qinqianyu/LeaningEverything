package team.gjz.database.redis.queue;


import team.gjz.database.redis.pool.RedisPoolUtil4J;
import redis.clients.jedis.Jedis;

/**
 * Created with IntelliJ IDEA.
 * Description: Redis PubSub 生产者
 * User: leisurexi
 * Date: 2019-10-12
 * Time: 10:32 下午
 */
public class Product {

    public static void main(String[] args) {
        Jedis jedis = RedisPoolUtil4J.getConnection();
        jedis.publish("mychannel", "python comes");
        jedis.publish("mychannel", "java comes");
        jedis.publish("mychannel", "golang comes");
    }

}
