package team.gjz.database.redis.hash;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import team.gjz.database.redis.pool.RedisPoolUtil4J;

import java.util.HashMap;
import java.util.Random;

import static team.gjz.database.redis.keys.redisKeys.hashKey;

/**
 * @description: 测试hash结构的存储和查询效率
 * @author: jxk
 * @create: 2020-03-24 11:39
 **/
public class Hashtest {
    @Test
    public void test1() {
        HashMap<String, String> hashMap = new HashMap<>();
        for (int i = 0; i < 3000; i++) {
            hashMap.put(i + "", i + "");
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < 50000; i++) {
            hashMap.get(String.valueOf(new Random().nextInt(3000)));
        }
        long end = System.currentTimeMillis();
        System.out.println(hashMap.size()/(end - start));
    }

    @Test
    public void test2() {
        for (int i = 0; i < 3000; i++) {
            try (Jedis jedis = RedisPoolUtil4J.getConnection()) {
                jedis.hset(hashKey, i + "", i + "");
            }
        }
        System.out.println("*******");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            try (Jedis jedis = RedisPoolUtil4J.getConnection()) {
                jedis.hget(hashKey, String.valueOf(new Random().nextInt(3000)));
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(50000.0/(end - start));
    }
}
