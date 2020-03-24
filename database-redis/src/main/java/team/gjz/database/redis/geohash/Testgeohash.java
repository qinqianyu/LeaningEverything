package team.gjz.database.redis.geohash;


import team.gjz.database.redis.pool.RedisPoolUtil4J;
import org.junit.Test;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Random;

import static team.gjz.database.redis.keys.redisKeys.geoHashKey;

/**
 * 测试geohash；
 */
public class Testgeohash {
    private static final double EARTH_RADIUS = 6378137;// 赤道半径(单位m)

    /**
     * 转化为弧度(rad)
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }


    /**
     * 用来测试存储的平均误差
     */
    @Test
    public void mytest() {
        Jedis jedis = RedisPoolUtil4J.getConnection();
        String mykey = geoHashKey;
        double log = 116.48115;
        double lat = 39.996744;
        double count = 0;
        int size = 1000;
        for (int i = 0; i < size; i++) {
            jedis.geoadd(mykey, log, lat, "c" + i);
            log += new Random().nextInt(50) * 0.000001;
            lat += new Random().nextInt(50) * 0.000001;
            List<GeoCoordinate> geopos = jedis.geopos(mykey, "c" + i);
            GeoCoordinate geoCoordinate = geopos.get(0);
            count += GetDistance(log, lat, geoCoordinate.getLongitude(), geoCoordinate.getLatitude());
        }
        System.out.println(count / size);
        jedis.close();
    }

    /**
     * 基于googleMap中的算法得到两经纬度之间的距离,计算精度与谷歌地图的距离精度差不多，相差范围在0.2米以下（单位m）
     *
     * @param lon1 第一点的精度
     * @param lat1 第一点的纬度
     * @param lon2 第二点的精度
     * @param lat2 第二点的纬度
     * @return 返回的距离，单位m
     */
    public static double GetDistance(double lon1, double lat1, double lon2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lon1) - rad(lon2);
        double s = 2 * Math.asin(Math.sqrt(
                Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        // s = Math.round(s * 10000) / 10000;
        return s;
    }
}



