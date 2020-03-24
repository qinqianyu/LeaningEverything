package team.gjz.database.mysql.to;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class MysqlToFiel {
    private static String url = "jdbc:mysql://192.168.20.143:3306/data_import";
    private static String user = "root";
    private static String password = "admin123!@#";

    public static Connection getConn() {
        Connection conn;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    static String outfiel = "C:/Users/24109/Desktop/青岛/企业名单.txt";

    public static void main(String[] args) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outfiel));
             Connection connection = getConn()) {
            for (int i = 0; i <= 3190000; i+=10000) {
                getstate(i,i+10000,writer,connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param a [起始id
     * @param b (结束id
     * @param writer 文件写入
     * @param connection 数据库连接
     * @throws SQLException
     * @throws IOException
     */
    public static void getstate(int a,int b, BufferedWriter writer,Connection connection) throws SQLException, IOException {
        String sql = "SELECT entname FROM `les_organization` where "+a+"<ouid and ouid<="+b;
        PreparedStatement statement = connection.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String resultSetString = resultSet.getString(1);
            writer.append(resultSetString);
            writer.newLine();
        }
    }
}
