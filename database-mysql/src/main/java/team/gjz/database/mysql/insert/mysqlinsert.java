package team.gjz.database.mysql.insert;


import team.gjz.database.mysql.RandomStringSample;

import java.sql.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class mysqlinsert {
    private String url = "jdbc:database.mysql://localhost:3306/database.neo4j?serverTimezone=UTC&rewriteBatchedStatements=true";

    private String user = "root";
    private String password = "123456";
    private int count = 300 * 10000;

    public static void main(String[] args) throws ClassNotFoundException {
        new mysqlinsert().Test();
    }

    private void Test() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            //计时开始
            Long startTime = System.currentTimeMillis();
            insertcompany(conn);
            insertpeople(conn);
            insertrelationship(conn);
            Long endTime = System.currentTimeMillis();
            System.out.println("OK,用时：" + (endTime - startTime) / 1000);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void insertcompany(Connection conn) throws SQLException {
        String randomJianHan;
        String sql = "insert into company (name) VALUES(?)";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            for (int i = 1; i <= count; i++) {
                randomJianHan = "公-" + RandomStringSample.getRandomJianHan(3);
                pstm.setString(1, randomJianHan);
                pstm.addBatch();
            }
            pstm.executeBatch();
            conn.commit();
        }
    }

    private void insertpeople(Connection conn) throws SQLException {
        String randomJianHan;
        String sql = "insert into people (name) VALUES(?)";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            for (int i = 1; i <= count; i++) {
                randomJianHan = "人-" + RandomStringSample.getRandomJianHan(3);
                pstm.setString(1, randomJianHan);
                pstm.addBatch();
            }
            pstm.executeBatch();
            conn.commit();
        }

    }

    private void insertrelationship(Connection conn) throws SQLException {
        String sql = "insert into relationship (start,end,end2,end3,end4) VALUES(?,?,?,?,?)";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            Set<Integer> integers = new HashSet<>();
            for (int i = 1; i <= count; i++) {
                integers.add(i);
                pstm.setInt(1, i);
                pstm.setInt(2, getRadomInt(integers));
                pstm.setInt(3, getRadomInt(integers));
                pstm.setInt(4, getRadomInt(integers));
                pstm.setInt(5, getRadomInt(integers));
                pstm.addBatch();
                integers.clear();
            }
            pstm.executeBatch();
            conn.commit();
        }
    }


    /**
     *
     * @param integers 已经产生的随机数集合
     * @return 产生集合以外的随机数
     */
    private int getRadomInt(Set<Integer> integers) {
        int a;
        Random rand = new Random();
        do {
            a = rand.nextInt(count) + 1;
        }
        while (integers.contains(a));
        integers.add(a);
        return a;
    }

}