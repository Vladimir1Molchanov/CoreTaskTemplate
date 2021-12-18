package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.*;

public class Util {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/users" +
            "?useSSL=false" +
            "&serverTimezone=UTC";

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

    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration()
                .setProperty("hibernate.connection.driver_class",
                        "com.mysql.jdbc.Driver")
                .setProperty("hibernate.connection.url",
                        URL)
                .setProperty("hibernate.connection.username",
                        USERNAME)
                .setProperty("hibernate.connection.password",
                        PASSWORD)
//                .setProperty("hibernate.connection.pool_size", "1")
                .setProperty("hibernate.connection.autocommit", "false")
                .setProperty("hdm2ddl.auto","update")
//                .setProperty("hibernate.cache.provider_class",
//                        "org.hibernate.cache.NoCacheProvider")
//                .setProperty("hibernate.cache.use_second_level_cache",
//                        "false")
//                .setProperty("hibernate.cache.use_query_cache", "false")
                .setProperty("hibernate.dialect",
                        "org.hibernate.dialect.MySQLDialect")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("spring.jpa.hibernate.naming.physical-strategy",
                        "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl"
                )
//                .setProperty("hibernate.current_session_context_class",
//                        "thread")
                .addPackage("ru.miralab.db")
                .addAnnotatedClass(User.class);
        StandardServiceRegistryBuilder b = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());

        return configuration.buildSessionFactory(b.build());
    }
}
