package com.themaestrocode.aaualms.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/aaua_lms_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "sqlpassword2023";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

}
