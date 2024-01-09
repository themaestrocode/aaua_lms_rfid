package com.themaestrocode.aaualms.service;

import com.themaestrocode.aaualms.entity.User;
import com.themaestrocode.aaualms.repository.UserRepository;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {

    public boolean saveStudent(User student) {
        UserRepository userRepository = new UserRepository();

        if(validateUserLibraryId(student.getUserLibraryId()) && validateUserId(student.getUserId()) && validateFirstName(student.getFirstName()) &&
                validateLastName(student.getLastName()) && validateImageUpload(student.getImagePath()) && validateFaculty(student.getFaculty()) &&
                validateDepartment(student.getDepartment()) && validateLevel(student.getLevel()) && validatePhoneNumber(student.getPhoneNumber()) &&
                validateEmail(student.getEmail())) {
            return userRepository.saveStudent(student);
        }

        if(!validateUserId(student.getUserId())) {
            setTextFieldAttribute(student.getUserId());
        }
        else {
            if(!student.getUserId().getText().equals("invalid or empty!")) {
                reverseTextColor(student.getUserId());
            }
        }

        if(!validateFirstName(student.getFirstName())) {
            setTextFieldAttribute(student.getFirstName());
        }
        else {
            if(!student.getFirstName().getText().equals("invalid or empty!")) {
                reverseTextColor(student.getFirstName());
            }
        }

        if(!validateLastName(student.getLastName())) {
            setTextFieldAttribute(student.getLastName());
        }
        else {
            if(!student.getLastName().getText().equals("invalid or empty!")) {
                reverseTextColor(student.getLastName());
            }
        }

        if(!validateFaculty(student.getFaculty())) {
            setTextFieldAttribute(student.getFaculty());
        }
        else {
            if(!student.getFaculty().getText().equals("invalid or empty!")) {
                reverseTextColor(student.getFaculty());
            }
        }

        if(!validateDepartment(student.getDepartment())) {
            setTextFieldAttribute(student.getDepartment());
        }
        else {
            if(!student.getDepartment().getText().equals("invalid or empty!")) {
                reverseTextColor(student.getDepartment());
            }
        }

        if(!validateLevel(student.getLevel())) {
            setTextFieldAttribute(student.getLevel());
        }
        else {
            if(!student.getLevel().getText().equals("invalid or empty!")) {
                reverseTextColor(student.getLevel());
            }
        }

        if(!validatePhoneNumber(student.getPhoneNumber())) {
            setTextFieldAttribute(student.getPhoneNumber());
        }
        else {
            if(!student.getPhoneNumber().getText().equals("invalid or empty!")) {
                reverseTextColor(student.getPhoneNumber());
            }
        }

        if(!validateEmail(student.getEmail())) {
            setTextFieldAttribute(student.getEmail());
        }
        else {
            if(!student.getEmail().getText().equals("invalid or empty!")) {
                reverseTextColor(student.getEmail());
            }
        }
        return false;
    }

    public boolean saveStaff(User staff) {
        UserRepository userRepository = new UserRepository();

        if(validateUserLibraryId(staff.getUserLibraryId()) && validateUserId(staff.getUserId()) && validateFirstName(staff.getFirstName()) &&
                validateLastName(staff.getLastName()) && validateImageUpload(staff.getImagePath()) && validateFaculty(staff.getFaculty()) &&
                validateDepartment(staff.getDepartment()) && validatePhoneNumber(staff.getPhoneNumber()) && validateEmail(staff.getEmail())) {
            return userRepository.saveStaff(staff);
        }

        if(!validateUserId(staff.getUserId())) {
            setTextFieldAttribute(staff.getUserId());
        }
        else {
            if(!staff.getUserId().getText().equals("invalid or empty!")) {
                reverseTextColor(staff.getUserId());
            }
        }

        if(!validateFirstName(staff.getFirstName())) {
            setTextFieldAttribute(staff.getFirstName());
        }
        else {
            if(!staff.getFirstName().getText().equals("invalid or empty!")) {
                reverseTextColor(staff.getFirstName());
            }
        }

        if(!validateLastName(staff.getLastName())) {
            setTextFieldAttribute(staff.getLastName());
        }
        else {
            if(!staff.getLastName().getText().equals("invalid or empty!")) {
                reverseTextColor(staff.getLastName());
            }
        }

        if(!validateFaculty(staff.getFaculty())) {
            setTextFieldAttribute(staff.getFaculty());
        }
        else {
            if(!staff.getFaculty().getText().equals("invalid or empty!")) {
                reverseTextColor(staff.getFaculty());
            }
        }

        if(!validateDepartment(staff.getDepartment())) {
            setTextFieldAttribute(staff.getDepartment());
        }
        else {
            if(!staff.getDepartment().getText().equals("invalid or empty!")) {
                reverseTextColor(staff.getDepartment());
            }
        }

        if(!validatePhoneNumber(staff.getPhoneNumber())) {
            setTextFieldAttribute(staff.getPhoneNumber());
        }
        else {
            if(!staff.getPhoneNumber().getText().equals("invalid or empty!")) {
                reverseTextColor(staff.getPhoneNumber());
            }
        }

        if(!validateEmail(staff.getEmail())) {
            setTextFieldAttribute(staff.getEmail());
        }
        else {
            if(!staff.getEmail().getText().equals("invalid or empty!")) {
                reverseTextColor(staff.getEmail());
            }
        }
        return false;
    }

    public boolean findUser(String userId) {
        UserRepository userRepository = new UserRepository();
        return userRepository.findUser(userId);
    }

    public List<User> getAllStudents() {
        UserRepository userRepository = new UserRepository();
        return userRepository.getAllStudents();
    }

    private boolean validateUserId(TextField userId) {
        if(userId.getText().isEmpty()) {
            System.out.println("user id not validated");
            return false;
        }
        return true;
    }

    private boolean validateFirstName(TextField firstName) {
        if(firstName.getText().isEmpty()) {
            System.out.println("first name not validated");
            return false;
        }
        return true;
    }

    private boolean validateLastName(TextField lastName) {
        if(lastName.getText().isEmpty()) {
            System.out.println("last name not validated");
            return false;
        }
        return true;
    }

    private boolean validateFaculty(TextField faculty) {
        if(faculty.getText().isEmpty()) {
            System.out.println("faculty not validated");
            return false;
        }
        return true;
    }

    private boolean validateDepartment(TextField department) {
        if(department.getText().isEmpty()) {
            System.out.println("department not validated");
            return false;
        }
        return true;
    }

    private boolean validateLevel(TextField level) {
        if(level.getText().isEmpty()) {
            System.out.println("level not validated");
            return false;
        }
        return true;
    }

    private boolean validatePhoneNumber(TextField phoneNumber) {
        if(phoneNumber.getText().length() == 11) {
            return true;
        }
        System.out.println("phone number not validated");
        return false;
    }

    private boolean validateEmail(TextField email) {
        String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email.getText());

        boolean matchSuccess = matcher.matches();

        if(matchSuccess) {
            return true;
        }
        System.out.println("email not validated");
        setTextFieldAttribute(email);
        return false;
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

    private void setTextFieldAttribute(TextField name) {
        name.setStyle("-fx-text-fill: #AA0000");
        name.setText("invalid or empty!");
    }

    private void reverseTextColor(TextField name) {
        name.setStyle("-fx-text-fill: black");
    }
}
