package com.themaestrocode.aaualms.repository;

import com.themaestrocode.aaualms.entity.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    public boolean findUser(String userId) {
        boolean userFound = false;

        try {
            Connection connection = DBConnector.connect();

            String query = "SELECT student_lib_id AS user_lib_id FROM student WHERE student_lib_id = ? " +
                    "UNION " +
                    "SELECT staff_lib_id AS user_lib_id FROM staff WHERE staff_lib_id = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userId);
            statement.setString(2, userId);

            ResultSet resultSet = statement.executeQuery();

            // Check if any rows are returned
            if (resultSet.next()) {
                userFound = true;
                String userLibId = resultSet.getString("user_lib_id");
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userFound;
    }

}
