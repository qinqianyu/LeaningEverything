package team.gjz.database.redis.pool;

import com.redislabs.modules.rejson.JReJSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: jxk
 * @create: 2020-03-04 09:15
 **/
public class myTest {

    public static void main(String[] args) {
        JReJSON jsonClient = RedisPoolUtil4J.getJsonClient();
        List<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        jsonClient.set("testkey", strings);
        List testkey = jsonClient.get("testkey", strings.getClass());
        System.out.println(testkey);
    }
}
