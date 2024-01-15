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

    public boolean addStudent(User student) {
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
                EventRepository eventRepository = new EventRepository();

                if(eventRepository.logEventForUserRegistration(student)) {
                    result = true;
                }
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean addStaff(User staff) {
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
                EventRepository eventRepository = new EventRepository();

                if(eventRepository.logEventForUserRegistration(staff)) {
                    result = true;
                }
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public User findUserByLibraryID(String userLibraryId) {
        User user = findStudentByLibraryId(userLibraryId);

        if(user == null) {
            user = findStaffByLibraryId(userLibraryId);
        }
        return user;
    }

    public User findStudentByLibraryId(String studentLibraryId) {
        User student = null;

        try {
            Connection connection = DBConnector.connect();

            String query = "SELECT * FROM students WHERE student_lib_id = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, studentLibraryId);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                String userLibraryID = resultSet.getString("student_lib_id");
                String userId = resultSet.getString("matric_no");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String imagePath = resultSet.getString("image_path");

                FacultyRepository facultyRepository = new FacultyRepository();
                Faculty faculty = facultyRepository.getFacultyByDepartmentId(resultSet.getInt("department_id"));

                String userFaculty = faculty.getFacultyName();

                DepartmentRepository departmentRepository = new DepartmentRepository();
                Department department = departmentRepository.getDepartmentById(resultSet.getInt("department_id"));

                String userDepartment = department.getDepartmentName();

                String level = resultSet.getString("current_level");
                String phoneNo = resultSet.getString("phone_no");
                String email = resultSet.getString("email");

                student = new User(userLibraryID, userId, firstName, lastName, imagePath, userFaculty, userDepartment, level, phoneNo, email);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return student;
    }

    public User findStaffByLibraryId(String staffLibraryId) {
        User staff = null;

        try {
            Connection connection = DBConnector.connect();

            String query = "SELECT * FROM staff WHERE staff_lib_id = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, staffLibraryId);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                String userLibraryID = resultSet.getString("staff_lib_id");
                String userId = resultSet.getString("staff_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String imagePath = resultSet.getString("image_path");

                FacultyRepository facultyRepository = new FacultyRepository();
                Faculty faculty = facultyRepository.getFacultyByDepartmentId(resultSet.getInt("department_id"));

                String userFaculty = faculty.getFacultyName();

                DepartmentRepository departmentRepository = new DepartmentRepository();
                Department department = departmentRepository.getDepartmentById(resultSet.getInt("department_id"));

                String userDepartment = department.getDepartmentName();

                String phoneNo = resultSet.getString("phone_no");
                String email = resultSet.getString("email");

                staff = new User(userLibraryID, userId, firstName, lastName, imagePath, userFaculty, userDepartment, phoneNo, email);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return staff;
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
                String userLibraryID = resultSet.getString("student_lib_id");
                String userId = resultSet.getString("matric_no");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String imagePath = resultSet.getString("image_path");

                FacultyRepository facultyRepository = new FacultyRepository();
                Faculty faculty = facultyRepository.getFacultyByDepartmentId(resultSet.getInt("department_id"));

                String userFaculty = faculty.getFacultyName();

                DepartmentRepository departmentRepository = new DepartmentRepository();
                Department department = departmentRepository.getDepartmentById(resultSet.getInt("department_id"));

                String userDepartment = department.getDepartmentName();

                String level = resultSet.getString("current_level");
                String phoneNo = resultSet.getString("phone_no");
                String email = resultSet.getString("email");

                student = new User(userLibraryID, userId, firstName, lastName, imagePath, userFaculty, userDepartment, level, phoneNo, email);
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
                String userLibraryID = resultSet.getString("staff_lib_id");
                String userId = resultSet.getString("staff_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String imagePath = resultSet.getString("image_path");

                FacultyRepository facultyRepository = new FacultyRepository();
                Faculty faculty = facultyRepository.getFacultyByDepartmentId(resultSet.getInt("department_id"));

                String userFaculty = faculty.getFacultyName();

                DepartmentRepository departmentRepository = new DepartmentRepository();
                Department department = departmentRepository.getDepartmentById(resultSet.getInt("department_id"));

                String userDepartment = department.getDepartmentName();

                String phoneNo = resultSet.getString("phone_no");
                String email = resultSet.getString("email");

                staff = new User(userLibraryID, userId, firstName, lastName, imagePath, userFaculty, userDepartment, phoneNo, email);
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
