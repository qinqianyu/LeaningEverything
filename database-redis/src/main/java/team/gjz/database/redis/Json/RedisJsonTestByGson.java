package team.gjz.database.redis.Json;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.redislabs.modules.rejson.JReJSON;
import com.redislabs.modules.rejson.Path;
import team.gjz.database.redis.pool.RedisPoolUtil4J;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.jxk.database.redis.keys.redisKeys.jsonKey;

public class RedisJsonTestByGson {
    private String mykey = jsonKey;

    @Test
    public void test1() {
        JReJSON jsonClient = RedisPoolUtil4J.getJsonClient();
        long timeMillis = System.currentTimeMillis();
        CpuCut cpuCut = CpuCut.builder().time(new String[]{new SimpleDateFormat("HH:mm:ss").format(new Date(timeMillis))}).value(new Integer[]{25}).build();
        jsonClient.set(mykey, cpuCut);

        //拿到數組time
        JsonArray json = RedisPoolUtil4J.getJsonClient().get(mykey, JsonArray.class, new Path(".time"));
        Gson gson = new GsonBuilder().create();
        List<String> times = gson.fromJson(json, new TypeToken<List<String>>() {
        }.getType());
        System.out.println(times);

        // //拿到數組value
        json = RedisPoolUtil4J.getJsonClient().get(mykey, JsonArray.class, new Path(".value"));
        List<String> values = gson.fromJson(json, new TypeToken<List<String>>(){}.getType());
        System.out.println(values);
    }


}
