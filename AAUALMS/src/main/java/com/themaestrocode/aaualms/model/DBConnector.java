package com.themaestrocode.aaualms.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector {
    private static Properties properties = new Properties();

    static {
        try {
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDBurl() {
        return properties.getProperty("db.url");
    }

    public static String getDBusername() {
        return properties.getProperty("db.username");
    }

    public static String getDBpassword() {
        return properties.getProperty("db.password");
    }

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(getDBurl(), getDBusername(), getDBpassword());
    }

}
