package com.themaestrocode.aaualms.service;

import com.themaestrocode.aaualms.repository.UserRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {

    public boolean saveStudent(String userLibraryId, String userId, String firstName, String lastName, String imagePath, String department, String level, String phoneNumber, String email) {

    }

    public boolean saveStaff(String userLibraryId, String userId, String firstName, String lastName, String imagePath, String department, String phoneNumber, String email) {

    }

    private boolean validateUserLibraryId(String userLibraryId) {
        if(userLibraryId.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean validateUserId(String userId) {
        if(userId.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean validateFirstName(String firstName) {
        if(firstName.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean validateLastName(String lastName) {
        if(lastName.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean validateImageUpload(String imagePath) {
        if(imagePath.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean validateDepartment(String department) {
        if(department.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean validateLevel(String level) {
        if(level.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        if(phoneNumber.length() == 11) {
            return true;
        }
        return false;
    }

    private boolean validateEmail(String email) {
        String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);

        boolean matchSuccess = matcher.matches();

        if(matchSuccess) {
            return true;
        }
        return false;
    }

    public boolean findUser(String userId) {
        UserRepository userRepository = new UserRepository();
        return userRepository.findUser(userId);
    }
}
