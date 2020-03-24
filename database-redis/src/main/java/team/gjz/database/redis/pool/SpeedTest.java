package team.gjz.database.redis.pool;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @description:
 * @author: jxk
 * @create: 2020-03-18 11:20
 **/
public class SpeedTest {
    @Test
    public void testSpeed(){
        int size=50000000;
        long stsrt = System.currentTimeMillis();
        for (int i = 0; i <size; i++) {
            Jedis jedis = RedisPoolUtil4J.getConnection();
            jedis.close();
        }
        long end = System.currentTimeMillis();
        System.out.println((size+0.0)/(end-stsrt));
    }
}
