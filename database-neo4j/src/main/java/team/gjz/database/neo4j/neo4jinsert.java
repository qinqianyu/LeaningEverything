package team.gjz.database.neo4j;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.neo4j.driver.v1.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.neo4j.driver.v1.Values.parameters;

public class neo4jinsert implements AutoCloseable {
    private final Driver driver;
    private final Session session;


    private final static int bichsize = 100000;
    private final static int bichsize2 =1000;


    public neo4jinsert(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
        session = driver.session();
    }

    @Override
    public void close() throws Exception {
        session.close();
        driver.close();
    }

    public static void main(String... args) throws Exception {
        Connection conn = null;
        try (neo4jinsert greeter = new neo4jinsert("bolt://localhost:7687",
                "neo4j", "123456")) {
            tomysql tomysql = new tomysql();
            conn = tomysql.getConn();

            int start = 1, end = bichsize;
            ArrayList<Guanxi> getend = tomysql.getend(conn, start, end);
            //计时开始
            Long startTime = System.currentTimeMillis();
            long endtiome = 0;
            long neo4jtime = 0;
            long sqltime = 0;
            int count = 1;
            while (getend.size() != 0) {
//                System.out.println(count + "***********" + getend.size());
//                count++;
//                System.out.println("正在写入neo4j");
//                greeter.insert(getend);
//                greeter.insert2(getend);
//                greeter.insert2print(getend);
//                System.out.println("已经写入neo4j");
                start += bichsize;
                end += bichsize;
                endtiome = System.currentTimeMillis();
                neo4jtime += endtiome - startTime;
                System.out.println("正在取出数据" + start + "==" + end);
                getend = tomysql.getend(conn, start, end);
                System.out.println("已经取出数据" + start + "==" + end);
                startTime = System.currentTimeMillis();
                sqltime += startTime - endtiome;
            }
//            Long endTime = System.currentTimeMillis();
            System.out.println("OK,用时：" + neo4jtime / 1000 + "***" + sqltime / 1000);
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            conn.close();
        }
    }

    public void insert(ArrayList<Guanxi> getend) {
        Transaction tx = session.beginTransaction();
        for (Guanxi row : getend) {
            tx.run("merge (:gongsi{name:$name,gid:$gid})", parameters("name", row.getStartname(), "gid", row.getStart()));
//            tx.run("merge (:gongsi{name:$name,gid:$gid})", parameters("name", row.getEndname(), "gid", row.getEnd()));
//            tx.run("merge (:gongsi{name:$name,gid:$gid})", parameters("name", row.getEnd2name(), "gid", row.getEnd2()));
//            tx.run("merge (:person{name:$name,pid:$pid})", parameters("name", row.getEnd3name(), "pid", row.getEnd3()));
//            tx.run("merge (:person{name:$name,pid:$pid})", parameters("name", row.getEnd4name(), "pid", row.getEnd4()));
        }
        tx.success();
        tx.close();
//        tx = session.beginTransaction();
//        for (Guanxi row : getend) {
//            tx.run("match (a:gongsi{name:$fname,gid:$fgid}),(b:gongsi{name:$tname,gid:$tgid}) merge (a)-[:投资]->(b)", parameters("fname", row.getStartname(), "fgid", row.getStart(), "tname", row.getEndname(), "tgid", row.getEnd()));
//            tx.run("match (a:gongsi{name:$fname,gid:$fgid}),(b:gongsi{name:$tname,gid:$tgid}) merge (a)-[:投资]->(b)", parameters("fname", row.getStartname(), "fgid", row.getStart(), "tname", row.getEnd2name(), "tgid", row.getEnd2()));
//            tx.run("match (a:person{name:$pname,pid:$pid}),(b:gongsi{name:$gname,gid:$gid}) merge (a)-[:任职]->(b)", parameters("pname", row.getEnd3name(), "pid", row.getEnd3(), "gname", row.getStartname(), "gid", row.getStart()));
//            tx.run("match (a:person{name:$pname,pid:$pid}),(b:gongsi{name:$gname,gid:$gid}) merge (a)-[:任职]->(b)", parameters("pname", row.getEnd4name(), "pid", row.getEnd4(), "gname", row.getStartname(), "gid", row.getStart()));
//        }
//        tx.success();
//        tx.close();
    }

    public void insert2(List<Guanxi> getend) {
        final int Max_send = bichsize2;
        int limit = (int) Math.ceil((double) getend.size() / (double) Max_send);
        Stream.iterate(0, n -> n + 1).limit(limit).forEach(a -> {
            System.out.println("***********"+System.currentTimeMillis());
            List<Guanxi> sendlist = getend.parallelStream().skip(a * Max_send).limit(Max_send).collect(Collectors.toList());
            String nodes = JSONArray.parseArray(JSON.toJSONString(sendlist)).toString().replaceAll("\"(\\w+)\"(\\s*:\\s*)", "$1$2");
            String cypher=new StringBuilder()
                    .append("UNWIND " + nodes + " as row\n")
                    .append( "CALL apoc.cypher.doIt('merge (n:gongsi{gid:{gid},name:{name}})',{gid: row.start, name: row.startname})")
                    .append(" YIELD value return 1")
                    .toString();
            System.out.println("-----------"+System.currentTimeMillis());
            Transaction tx = session.beginTransaction();
            tx.run(cypher);
            tx.success();
            tx.close();
            System.out.println("==========="+System.currentTimeMillis());
        });
    }
    public void insert2print(ArrayList<Guanxi> getend) {
        final int Max_send = bichsize2;
        int limit = (int) Math.ceil((double) getend.size() / (double) Max_send);
        Stream.iterate(0, n -> n + 1).limit(limit).forEach(a -> {
            List<Guanxi> sendlist = getend.parallelStream().skip(a * Max_send).limit(Max_send).collect(Collectors.toList());
            System.out.println("********");
            for (Guanxi tmp : sendlist) {
                System.out.println(tmp);
            }
            String nodes = JSONArray.parseArray(JSON.toJSONString(sendlist)).toString().replaceAll("\"(\\w+)\"(\\s*:\\s*)", "$1$2");
            System.out.println(nodes);
        });
    }

}

