package team.gjz.database.redis.zset;

import team.gjz.database.redis.pool.RedisPoolUtil4J;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @description:
 * @author: jxk
 * @create: 2020-03-06 08:58
 **/
public class zsetUntil {
    @Test
    public void test1() {
        Jedis jedis = RedisPoolUtil4J.getConnection();
        jedis.del("zsetkey");
        jedis.zadd("zsetkey", 0, "河北省");
        jedis.zadd("zsetkey", 0, "河北省邢台市");
        jedis.zadd("zsetkey", 0, "河北省邯郸市");
        jedis.zadd("zsetkey", 0, "河南省");
        Set<String> zsetkey = jedis.zrangeByLex("zsetkey", "[河北省", "(" + strPlusOne("河北省"));
        zsetkey.forEach(System.out::println);
        jedis.del("zsetkey");
        jedis.close();
    }

     public static String strPlusOne(String str) {
        if (str == null || str.length() < 1) {
            System.out.println("传入字符为空，无法加1");
            return null;
        }
        String substring = str.substring(0, str.length() - 1);
        char endchar = str.charAt(str.length() - 1);
        String endstr = String.valueOf(endchar);
        byte[] bytes = intToByteArray(byteArrayToInt(endstr.getBytes()) + 1);
        assert bytes != null;
        String s = new String(bytes);
        return substring + s;
    }

    private static Integer byteArrayToInt(byte[] b) {
        switch (b.length) {
            case 1:
                return b[0] & 0xFF;
            case 2:
                return b[1] & 0xFF | (b[0] & 0xFF) << 8;
            case 3:
                return b[2] & 0xFF | (b[1] & 0xFF) << 8 | (b[0] & 0xFF) << 16;
            case 4:
                return b[3] & 0xFF | (b[2] & 0xFF) << 8 | (b[1] & 0xFF) << 16 | (b[0] & 0xFF) << 24;
            default:
                return null;
        }
    }

    private static byte[] intToByteArray(Integer a) {
        if (((a >> 24) & 0xFF) > 0) {
            return new byte[]{
                    (byte) ((a >> 24) & 0xFF),
                    (byte) ((a >> 16) & 0xFF),
                    (byte) ((a >> 8) & 0xFF),
                    (byte) (a & 0xFF)
            };
        }
        if (((a >> 16) & 0xFF) > 0) {
            return new byte[]{
                    (byte) ((a >> 16) & 0xFF),
                    (byte) ((a >> 8) & 0xFF),
                    (byte) (a & 0xFF)
            };
        }
        if (((a >> 8) & 0xFF) > 0) {
            return new byte[]{
                    (byte) ((a >> 8) & 0xFF),
                    (byte) (a & 0xFF)
            };
        }
        if ((a & 0xFF) > 0) {
            return new byte[]{
                    (byte) (a & 0xFF)
            };
        }
        return null;
    }
}
