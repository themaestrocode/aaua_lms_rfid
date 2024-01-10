package com.themaestrocode.aaualms.repository;

import com.themaestrocode.aaualms.utility.DBConnector;
import com.themaestrocode.aaualms.entity.Faculty;
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

            String query = "SELECT faculty_name FROM faculties";

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

    public Faculty getFacultyByDepartmentId(int deprtmentId) {
        Faculty faculty = null;

        try {
            Connection connection = DBConnector.connect();

            String query = "SELECT * FROM faculties WHERE faculty_id IN (SELECT faculty_id FROM departments WHERE department_id = ?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, deprtmentId);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                faculty = new Faculty(resultSet.getInt("faculty_id"), resultSet.getString("faculty_name"));
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return faculty;
    }
}
