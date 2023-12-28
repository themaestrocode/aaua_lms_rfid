package com.themaestrocode.aaualms.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector {
    private static Properties properties = new Properties();
    private static final String URL = "jdbc:mysql://localhost:3306/aaua_lms_db";

    static {
        try {
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDBusername() {
        return properties.getProperty("db.username");
    }

    public static String getDBpassword() {
        return properties.getProperty("db.password");
    }

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

}
