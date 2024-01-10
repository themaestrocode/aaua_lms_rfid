package com.themaestrocode.aaualms.utility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private static String url, username, password, filePath = "C:\\Users\\VICTOR A. SODERU\\Documents\\My Files\\mysql details.txt";

    public static Connection connect() throws SQLException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));

            url = bufferedReader.readLine();
            username = bufferedReader.readLine();
            password = bufferedReader.readLine();
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return DriverManager.getConnection(url, username, password);
    }
}
