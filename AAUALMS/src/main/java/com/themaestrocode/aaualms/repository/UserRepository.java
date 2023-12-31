package com.themaestrocode.aaualms.repository;

import com.themaestrocode.aaualms.entity.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    public boolean findUser(String userId) {
        boolean userFound = false;
        String user = "";

        try {
            Connection connection = DBConnector.connect();

            String query = "SELECT student_lib_id FROM student WHERE student_lib_id = '" + userId + "'";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                user = resultSet.getString("student_lib_id");
            }

            if(userId.equals(user)) {
                userFound = true;
            }

            if(!userFound) {
                query = "SELECT staff_lib_id FROM staff WHERE staff_lib_id = '" + userId + "'";

                statement = connection.prepareStatement(query);
                resultSet = statement.executeQuery();

                while(resultSet.next()) {
                    user = resultSet.getString("staff_lib_id");
                }

                if(userId.equals(user)) {
                    userFound = true;
                }
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
