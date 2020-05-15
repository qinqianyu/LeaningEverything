package team.gjz.database.redis.increID;


import redis.clients.jedis.Jedis;
import team.gjz.database.redis.pool.RedisPoolUtil4J;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @description: 自增ID
 * @author: jxk
 * @create: 2020-04-07 15:16
 **/
public class IncreID {


    /**
     * 获取有过期时间的自增长ID
     *
     * @param key
     * @return
     */
    public long generate(String key) {
        try (Jedis jedis = RedisPoolUtil4J.getConnection();) {
            Long ttl = jedis.ttl(key);
            if (ttl == -2 || ttl == -1) {
                jedis.expire(key, 20);
            }
            return jedis.incr(key);
        }
    }


    public String generateOrderId() {
        //生成id为当前日期（yyMMddHHmmss）+6位（从000000开始不足位数补0）
        LocalDateTime now = LocalDateTime.now();
        String orderIdPrefix = getOrderIdPrefix(now);//生成yyyyMMddHHmmss
        return orderIdPrefix + String.format("%1$05d", generate(orderIdPrefix));
    }

    public static String getOrderIdPrefix(LocalDateTime now) {
        return now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

}
