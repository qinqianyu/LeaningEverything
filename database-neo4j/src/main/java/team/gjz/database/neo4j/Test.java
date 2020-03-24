package team.gjz.database.neo4j;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Test {
    private final Map<String, Integer> map;

    {
        map = new HashMap<>();
        map.put("个人合伙执照", 2);
        map.put("个人独资执照", 2);
        map.put("个体工商执照", 2);
        map.put("中华人民共和国军官证", 1);
        map.put("中华人民共和国居民身份证", 1);
        map.put("中华人民共和国警官证", 1);
        map.put("公司执照", 2);
        map.put("其他类型", 0);
        map.put("其它有效身份证件", 1);
        map.put("分公司执照", 2);
        map.put("外商承包企业执照", 2);
        map.put("外商投资企业执照", 2);
        map.put("外国（地区）护照", 1);
        map.put("居民证", 1);
        map.put("澳门居民身份证", 1);
        map.put("营业执照", 2);
        map.put("通行证", 1);
        map.put("集团登记证", 2);
        map.put("非公司制企业法人执照", 2);
        map.put("香港居民身份证", 1);
    }

    public static void main(String[] args) {
        String row="\"mid_mkt_menber\",\"500101610393346\",\"易巧玲\",\"510704198610201222\",\"\"";
        String split[] =  row.replace("\"", "").split(",",-1);
        System.out.println(split.length);
        if(split.length < 5 ){
            System.out.println(1);
        }
        if(split[3].length() < 6  ){
            System.out.println(2);
        }
        if(split[1].length() < 6 ){
            System.out.println(3);
        }
        if(split[3].endsWith("*") ){
            System.out.println(4);
        }

    }

    int Judge(String str, String str1) {
        if (map.get(str) == 1) {
            return 1;
        } else if (map.get(str) == 2) {
            return 2;
        }
        if (str1.equals("自然人")) {
            return 1;
        }
        return 0;
    }

    public static void main1(String[] args) {
        Random rand = new Random();
        int a;
        for (int j = 0; j < 10; j++) {
            a = rand.nextInt(1);
        }
        Guanxi guanxi = new Guanxi(1, 2, 3, 4, 5, "s1", "s2", "s3", "s4", "s5");
        Map<String, Object> map = entityToMap(guanxi);
        String s = JSON.toJSONString(map);
        System.out.println(s);
        System.out.println(map.toString());
    }

    public static Map<String, Object> entityToMap(Object object) {
        Map<String, Object> map = new HashMap();
        for (Field field : object.getClass().getDeclaredFields()) {
            try {
                boolean flag = field.isAccessible();
                field.setAccessible(true);
                Object o = field.get(object);
                map.put(field.getName(), o);
                field.setAccessible(flag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
