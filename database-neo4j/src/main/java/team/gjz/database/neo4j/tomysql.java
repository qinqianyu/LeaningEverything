package team.gjz.database.neo4j;

import java.sql.*;
import java.util.ArrayList;

public class tomysql {
    private String url = "jdbc:database.mysql://192.168.20.134:3306/database.neo4j?serverTimezone=UTC&rewriteBatchedStatements=true";
    private String user = "hive";
    private String password = "bigdata";

    public Connection getConn() {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    ArrayList<Guanxi> getend(Connection conn, int a, int b) throws SQLException {
        ArrayList<Guanxi> guanxis = new ArrayList<Guanxi>(b-a);
        String sql = "select a.start start,b.name startname,a.end end,c.name endname,a.end2 end2,d.name end2name,a.end3 end3,e.name end3name,a.end4 end4,f.name end4name from (select * from guanxi g where g.id >=" + a + " and g.id<=" + b + ") a left join gongsi b on a.start=b.id left join gongsi c on a.end=c.id left join gongsi d on a.end2=d.id left join people e on a.end3=e.id left join people f on a.end4=f.id";
        PreparedStatement statement = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Guanxi guanxi = new Guanxi();
            guanxi.setStart(resultSet.getInt(1));
            guanxi.setStartname(resultSet.getString(2));
            guanxi.setEnd(resultSet.getInt(3));
            guanxi.setEndname(resultSet.getString(4));
            guanxi.setEnd2(resultSet.getInt(5));
            guanxi.setEnd2name(resultSet.getString(6));
            guanxi.setEnd3(resultSet.getInt(7));
            guanxi.setEnd3name(resultSet.getString(8));
            guanxi.setEnd4(resultSet.getInt(9));
            guanxi.setEnd4name(resultSet.getString(10));
            guanxis.add(guanxi);
        }
        resultSet.close();
        statement.close();
        return guanxis;
    }


}
