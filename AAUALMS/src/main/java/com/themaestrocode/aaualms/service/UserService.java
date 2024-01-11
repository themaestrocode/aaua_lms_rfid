package com.themaestrocode.aaualms.service;

import com.themaestrocode.aaualms.entity.User;
import com.themaestrocode.aaualms.repository.UserRepository;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {

    String addUserPageTextFieldsErrorMessage = "invalid or empty!";
    UserRepository userRepository = new UserRepository();

    public boolean saveStudent(User student) {
        if(validateUserLibraryId(student.getUserLibraryId()) && validateUserId(student.getUserId()) && validateFirstName(student.getFirstName()) &&
                validateLastName(student.getLastName()) && validateImageUpload(student.getImagePath()) && validateFaculty(student.getFaculty()) &&
                validateDepartment(student.getDepartment()) && validateLevel(student.getLevel()) && validatePhoneNumber(student.getPhoneNumber()) &&
                validateEmail(student.getEmail())) {
            return userRepository.saveStudent(student);
        }
        return false;
    }

    public boolean saveStaff(User staff) {
        if(validateUserLibraryId(staff.getUserLibraryId()) && validateUserId(staff.getUserId()) && validateFirstName(staff.getFirstName()) &&
                validateLastName(staff.getLastName()) && validateImageUpload(staff.getImagePath()) && validateFaculty(staff.getFaculty()) &&
                validateDepartment(staff.getDepartment()) && validatePhoneNumber(staff.getPhoneNumber()) && validateEmail(staff.getEmail())) {
            return userRepository.saveStaff(staff);
        }
        return false;
    }

    public User findUserByLibraryId(String userId) {
        return userRepository.findUserByLibraryID(userId);
    }

    public List<User> getAllStudents() {
        return userRepository.getAllStudents();
    }

    public List<User> getAllStaff() {
        return userRepository.getAllStaff();
    }

    public boolean validateUserId(String userId) {
        if(userId == null || userId.equals(addUserPageTextFieldsErrorMessage)) {
            System.out.println("user id not validated");
            return false;
        }
        return true;
    }

    public boolean validateFirstName(String firstName) {
        if(firstName == null || firstName.equals(addUserPageTextFieldsErrorMessage)) {
            System.out.println("first name not validated");
            return false;
        }
        return true;
    }

    public boolean validateLastName(String lastName) {
        if(lastName == null || lastName.equals(addUserPageTextFieldsErrorMessage)) {
            System.out.println("last name not validated");
            return false;
        }
        return true;
    }

    public boolean validateFaculty(String faculty) {
        if(faculty == null || faculty.equals(addUserPageTextFieldsErrorMessage)) {
            System.out.println("faculty not validated");
            return false;
        }
        return true;
    }

    public boolean validateDepartment(String department) {
        if(department == null || department.equals(addUserPageTextFieldsErrorMessage)) {
            System.out.println("department not validated");
            return false;
        }
        return true;
    }

    public boolean validateLevel(String level) {
        if(level == null || level.equals(addUserPageTextFieldsErrorMessage)) {
            System.out.println("level not validated");
            return false;
        }
        return true;
    }

    public boolean validatePhoneNumber(String phoneNumber) {
        if(phoneNumber.length() == 11 || phoneNumber.equals(addUserPageTextFieldsErrorMessage)) {
            return true;
        }
        System.out.println("phone number not validated");
        return false;
    }

    public boolean validateEmail(String email) {
        String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);

        boolean matchSuccess = matcher.matches();

        if(!matchSuccess || email.equals(addUserPageTextFieldsErrorMessage)) {
            System.out.println("email not validated");
            return false;
        }
        return true;
    }

    public boolean validateImageUpload(String imagePath) {
        if(imagePath == null) {
            System.out.println("image not yet uploaded!");
            return false;
        }
        return true;
    }

    public boolean validateUserLibraryId(String userLibraryId) {
        if(userLibraryId == null) {
            System.out.println("user library id is null");
            return false;
        }
        return true;
    }
}
