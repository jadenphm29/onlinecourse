package mylib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

    private static final String DB_NAME     = "OnlineCourseDB";
    private static final String DB_USER_NAME = "sa";
    private static final String DB_PASSWORD  = "12345";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        String url = "jdbc:sqlserver://localhost:1433;"
                   + "databaseName=" + DB_NAME + ";"
                   + "useUnicode=true;"
                   + "characterEncoding=UTF-8;"
                   + "encrypt=false";

        return DriverManager.getConnection(url, DB_USER_NAME, DB_PASSWORD);
    }
}