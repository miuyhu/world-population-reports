package com.worldreports.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {

    private DatabaseConnection() {
        // Prevent this utility class from being instantiated.
    }

    public static Connection getConnection() throws SQLException {
        String url = System.getenv().getOrDefault(
                "DB_URL",
                "jdbc:mysql://localhost:3306/world"
        );

        String username = System.getenv().getOrDefault(
                "DB_USER",
                "root"
        );

        String password = System.getenv("DB_PASSWORD");

        if (password == null || password.isBlank()) {
            throw new IllegalStateException(
                    "DB_PASSWORD environment variable has not been set."
            );
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException exception) {
            throw new IllegalStateException(
                    "MySQL JDBC driver is missing from the runtime classpath.",
                    exception
            );
        }

        return DriverManager.getConnection(
                url,
                username,
                password
        );
    }
}