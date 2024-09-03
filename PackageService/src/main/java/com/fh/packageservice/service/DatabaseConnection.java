package com.fh.packageservice.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String DB_DRIVER = "postgresql";
    private static final String DB_HOST = "localhost";
    private static final int DB_PORT = 30002;
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static final String DB_NAME = "postaldb";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(createConnectionString());
    }

    private static String createConnectionString() {
        return String.format(
                "jdbc:%s://%s:%d/%s?user=%s&password=%s",
                DB_DRIVER,
                DB_HOST,
                DB_PORT,
                DB_NAME,
                DB_USERNAME,
                DB_PASSWORD
        );
    }
}
