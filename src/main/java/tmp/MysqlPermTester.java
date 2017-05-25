/**
 * Created by dora on 2017/5/3.
 * Mysql批量插入的效率对比
 */
package tmp;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class MysqlPermTester {
    static String url="jdbc:mysql://100.69.199.197:3306/rgs?"
        +"user=root&password=1qaz@WSX&useUnicode=true&characterEncoding=UTF8";
    static int MAX_COUNT = 500;
    static String sql = "insert test1 value(%d, 'hello%d')";
/*
    public static void main(String[] args) throws Exception {
        System.out.println("Hello MySQL!");
        // 动态加载mysql驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("成功加载MySQL驱动程序");

        MysqlPermTester.insertMysql1();
        MysqlPermTester.insertMysql2();
    }
*/
    public static void insertMysql1() throws Exception{
        Connection conn=null;

        try {
            // 一个Connection代表一个数据库连接
            conn=DriverManager.getConnection(url);

            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
            Statement stmt=conn.createStatement();

            long timestamp1 = System.currentTimeMillis();
            for(int i = 0; i < MAX_COUNT; i++) {
                int result = stmt.executeUpdate(String.format(sql, System.currentTimeMillis(), i));
            }
            long timestamp2 = System.currentTimeMillis();
            System.out.println(String.format("Method1 Time:%d", timestamp2-timestamp1));

        }catch(SQLException e){
            System.out.println("MySQL操作错误");
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            conn.close();
        }
    }

    public static void insertMysql2() throws SQLException {
        Connection conn=null;

        try {
            // 一个Connection代表一个数据库连接
            conn=DriverManager.getConnection(url);
            conn.setAutoCommit(false);
            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
            Statement stmt=conn.createStatement();

            long timestamp1 = System.currentTimeMillis();
            for(int i = 0; i < MAX_COUNT; i++) {
                stmt.addBatch(String.format(sql, System.currentTimeMillis()+i, i));
            }
            stmt.executeBatch();
            conn.commit();
            long timestamp2 = System.currentTimeMillis();
            System.out.println(String.format("Method2(Batch) Time:%d", timestamp2-timestamp1));

        }catch(SQLException e){
            System.out.println("MySQL操作错误");
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            conn.close();
        }
    }
}
