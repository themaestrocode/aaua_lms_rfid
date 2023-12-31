package com.themaestrocode.aaualms.repository;

import com.themaestrocode.aaualms.entity.DBConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FacultyRepository {

    ObservableList<String> facultyList;
    public ObservableList<String> getAllFaculty() {
        facultyList = FXCollections.observableArrayList();

        try {
            Connection connection = DBConnector.connect();

            String query = "SELECT faculty_name FROM faculty";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                String facultyName = resultSet.getString("faculty_name");
                facultyList.add(facultyName);
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return facultyList;
    }
}
