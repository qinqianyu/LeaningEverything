package team.gjz.database.redis.string;

import team.gjz.database.redis.Json.beans.Guize;
import team.gjz.database.redis.pool.RedisPoolUtil4J;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

import static team.gjz.database.redis.keys.redisKeys.testKey;

/**
 * @description:
 * @author: jxk
 * @create: 2020-03-11 16:37
 **/
public class RedisStringTest {
    @Test
    public void testGuiZe() {
        List<String> na = new ArrayList<>();
        List<String> po = new ArrayList<>();
        na.add("去掉");
        na.add("不要");
        na.add("排除");
        po.add("第一");
        po.add("首个");
        List<String> po2 = new ArrayList<>();
        po2.add("第二");
        po2.add("亚军");
        List<List<String>> add = new ArrayList<>();
        List<List<String>> add2 = new ArrayList<>();
        add.add(na);
        add2.add(po);
        add2.add(po2);
        Guize build = Guize.builder().id("1").code("2002").na(add).po(add2).build();
        Jedis connection = RedisPoolUtil4J.getConnection();
        ArrayList<Guize> list = new ArrayList<>();
        list.add(build);
        list.add(build);
        connection.set(testKey.getBytes(),SerializeUtil.serialize(list));
        byte[] bytes = connection.get(testKey.getBytes());
        List<Guize> unserialize = (List<Guize>)SerializeUtil.unserialize(bytes);
        System.out.println(unserialize);
        connection.del(testKey);
        connection.close();
    }
}
