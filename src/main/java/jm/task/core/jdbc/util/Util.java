package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/users" +
            "?useSSL=false" +
            "&serverTimezone=UTC"
//            "&autoReconnect=true" +
//            "&failOverReadOnly=false" +
//            "&maxReconnects=10"
            ;

    public static Connection connect() {
        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            conn.setAutoCommit(false);
            return conn;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
