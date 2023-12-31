package com.themaestrocode.aaualms.repository;

import com.themaestrocode.aaualms.entity.DBConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentRepository {

    ObservableList<String> departmentList;
    public ObservableList<String> getFacultiesUnderSelectedDepartment(String facultyName) {
        departmentList = FXCollections.observableArrayList();

        try {
            Connection connection = DBConnector.connect();

            String query = "SELECT department_name FROM department WHERE faculty_id IN " +
                    "(SELECT faculty_id FROM faculty WHERE faculty_name = '" + facultyName + "')";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                String departmentName = resultSet.getString("department_name");
                departmentList.add(departmentName);
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return departmentList;
    }
}
