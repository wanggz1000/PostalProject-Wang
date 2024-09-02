package com.fh.api.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Data {

    public final static String DRIVER = "postgresql";
    public static String HOST = "localhost";
    public static int PORT = 30002;
    public final static String USERNAME = "postgres";
    public final static String PASSWORD = "postgres";
    public final static String DATABASE_NAME = "postaldb";


    public static Connection Connection() throws SQLException {
        return DriverManager.getConnection(URL());
    }

    private static String URL() {

        return String.format(
                "jdbc:%s://%s:%s/%s?user=%s&password=%s",
                DRIVER,
                HOST,
                PORT,
                DATABASE_NAME,
                USERNAME,
                PASSWORD
        );
    }
}
