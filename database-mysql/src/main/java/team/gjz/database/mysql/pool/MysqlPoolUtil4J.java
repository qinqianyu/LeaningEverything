package team.gjz.database.mysql.pool;


import java.sql.*;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;

/**
 * jdbc mysql 连接池工具类
 *
 * @author DELL
 */
@Slf4j
public class MysqlPoolUtil4J {

    private static BasicDataSource bs = null;

    /**
     * 创建数据源
     *
     * @return
     */
    static BasicDataSource getDataSource() {
        if (bs == null) {

            bs = new BasicDataSource();
            bs.setDriverClassName("com.mysql.cj.jdbc.Driver");
            bs.setUrl("jdbc:mysql://192.168.20.134:3306/neo4j");
            bs.setUsername("hive");
            bs.setPassword("bigdata");

            bs.setMaxTotal(20);         // 设置最大并发数
            bs.setInitialSize(3);         // 数据库初始化时，创建的连接个数
            bs.setMinIdle(2);             // 在不新建连接的条件下，池中保持空闲的最少连接数。
            bs.setMaxIdle(5);           // 池里不会被释放的最多空闲连接数量。设置为0时表示无限制。
            bs.setMaxWaitMillis(5000);    // 在抛出异常之前，池等待连接被回收的最长时间（当没有可用连接时）。设置为-1表示无限等待。
            bs.setMinEvictableIdleTimeMillis(10 * 1000);    // 空闲连接5秒中后释放
            bs.setTimeBetweenEvictionRunsMillis(1 * 60 * 1000);     //1分钟检测一次是否有死掉的线程
            bs.setTestOnBorrow(true);
        }
        return bs;
    }


    /**
     * 释放数据源
     */
    public void shutDownDataSource() throws SQLException {
        if (bs != null) {
            bs.close();
        }
    }

    /**
     * 获取数据库连接
     *
     * @return
     */
    public static Connection getConnection() {
        Connection con = null;
        try {
            if (bs != null) {
                con = bs.getConnection();
            } else {
                con = getDataSource().getConnection();
            }
        } catch (Exception e){
            log.error(e.getMessage());
        }
        return con;
    }


    /**
     * 关闭连接
     */
    public static void closeCon(ResultSet rs, PreparedStatement ps,Connection con) {
        if (rs != null) {
            try {
                rs.close();
            } catch(Exception e) {
                log.error(e.getMessage());
            }
        }
        if (ps != null) {
            try {
                ps.close();
            }catch(Exception e) {
                log.error(e.getMessage());
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch(Exception e) {
                log.error(e.getMessage());
            }
        }
    }
}