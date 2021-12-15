package jm.task.core.jdbc.util;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.*;

public class Util {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://localhost:3306/users" +
            "?useSSL=false" +
            "&serverTimezone=UTC";

    public Connection connect() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);

        } catch (SQLException e) {
            System.out.println("U-u-ups, something go wrong, check ->");
            e.printStackTrace();
        }
        return null;
    }
}
