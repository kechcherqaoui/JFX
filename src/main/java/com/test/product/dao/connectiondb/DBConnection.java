package com.test.product.dao.connectiondb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DB_NAME = "ADV_JAVA";
    private static final String HOST = "jdbc:mysql://localhost:3306/" + DB_NAME;
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "newpassword";
    private static final Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
