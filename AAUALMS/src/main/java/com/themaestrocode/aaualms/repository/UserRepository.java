package com.themaestrocode.aaualms.repository;

import com.themaestrocode.aaualms.utility.DBConnector;
import com.themaestrocode.aaualms.entity.Department;
import com.themaestrocode.aaualms.entity.Faculty;
import com.themaestrocode.aaualms.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    List<User> studentLibraryUsers, staffLibraryUsers;
    String userLibraryId, imagePath, userId, firstName, lastName, userFaculty, userDepartment, level, phoneNo, email;

    public boolean saveStudent(User student) {
        boolean result = false;

        DepartmentRepository departmentRepository = new DepartmentRepository();
        int departmentId = departmentRepository.findDepartmentByName(student.getDepartment());

        try {
            Connection connection = DBConnector.connect();

            String query = "INSERT INTO students VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, student.getUserLibraryId());
            statement.setString(2, student.getUserId());
            statement.setString(3, student.getFirstName());
            statement.setString(4, student.getLastName());
            statement.setString(5, student.getImagePath());
            statement.setInt(6, departmentId);
            statement.setString(7, student.getLevel());
            statement.setString(8, student.getPhoneNumber());
            statement.setString(9, student.getEmail());

            int rowInserted = statement.executeUpdate();

            if(rowInserted > 0) {
                result = true;
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean saveStaff(User staff) {
        boolean result = false;

        DepartmentRepository departmentRepository = new DepartmentRepository();
        int departmentId = departmentRepository.findDepartmentByName(staff.getDepartment());

        try {
            Connection connection = DBConnector.connect();

            String query = "INSERT INTO staff VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, staff.getUserLibraryId());
            statement.setString(2, staff.getUserId());
            statement.setString(3, staff.getFirstName());
            statement.setString(4, staff.getLastName());
            statement.setString(5, staff.getImagePath());
            statement.setInt(6, departmentId);
            statement.setString(7, staff.getPhoneNumber());
            statement.setString(8, staff.getEmail());

            int rowInserted = statement.executeUpdate();

            if(rowInserted > 0) {
                result = true;
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean findUser(String userLibraryId) {
        boolean userFound = false;

        try {
            Connection connection = DBConnector.connect();

            String query = "SELECT student_lib_id AS user_lib_id FROM students WHERE student_lib_id = ? " +
                    "UNION " +
                    "SELECT staff_lib_id AS user_lib_id FROM staff WHERE staff_lib_id = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userLibraryId);
            statement.setString(2, userLibraryId);

            ResultSet resultSet = statement.executeQuery();

            // Check if any rows are returned
            if(resultSet.next()) {
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

    public List<User> getAllStudents() {
        studentLibraryUsers = new ArrayList<>();
        User student = null;

        try {
            Connection connection = DBConnector.connect();

            String query = "SELECT * FROM students";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                userLibraryId = resultSet.getString("student_lib_id");
                userId = resultSet.getString("matric_no");
                firstName = resultSet.getString("first_name");
                lastName = resultSet.getString("last_name");
                imagePath = resultSet.getString("image_path");

                FacultyRepository facultyRepository = new FacultyRepository();
                Faculty faculty = facultyRepository.getFacultyByDepartmentId(resultSet.getInt("department_id"));

                userFaculty = faculty.getFacultyName();

                DepartmentRepository departmentRepository = new DepartmentRepository();
                Department department = departmentRepository.getDepartmentById(resultSet.getInt("department_id"));

                userDepartment = department.getDepartmentName();

                level = resultSet.getString("current_level");
                phoneNo = resultSet.getString("phone_no");
                email = resultSet.getString("email");

                student = new User(userLibraryId, userId, firstName, lastName, imagePath, userFaculty, userDepartment, level, phoneNo, email);
                studentLibraryUsers.add(student);
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return studentLibraryUsers;
    }

    public List<User> getAllStaff() {
        staffLibraryUsers = new ArrayList<>();
        User staff = null;

        try {
            Connection connection = DBConnector.connect();

            String query = "SELECT * FROM staff";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                userLibraryId = resultSet.getString("staff_lib_id");
                userId = resultSet.getString("staff_id");
                firstName = resultSet.getString("first_name");
                lastName = resultSet.getString("last_name");
                imagePath = resultSet.getString("image_path");

                FacultyRepository facultyRepository = new FacultyRepository();
                Faculty faculty = facultyRepository.getFacultyByDepartmentId(resultSet.getInt("department_id"));

                userFaculty = faculty.getFacultyName();

                DepartmentRepository departmentRepository = new DepartmentRepository();
                Department department = departmentRepository.getDepartmentById(resultSet.getInt("department_id"));

                userDepartment = department.getDepartmentName();

                phoneNo = resultSet.getString("phone_no");
                email = resultSet.getString("email");

                staff = new User(userLibraryId, userId, firstName, lastName, imagePath, userFaculty, userDepartment, phoneNo, email);
                staffLibraryUsers.add(staff);
            }
            // Close resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return staffLibraryUsers;
    }
}
