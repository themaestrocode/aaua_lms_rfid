package com.themaestrocode.aaualms.service;

import com.themaestrocode.aaualms.entity.User;
import com.themaestrocode.aaualms.repository.UserRepository;
import com.themaestrocode.aaualms.utility.UtilityMethods;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {

    String addUserPageTextFieldsErrorMessage = "invalid or empty!";
    UserRepository userRepository = new UserRepository();


    public User findUserByLibraryId(String userId) {
        return userRepository.findUserByLibraryID(userId);
    }

    public List<User> getAllStudents() {
        return userRepository.getAllStudents();
    }

    public List<User> getAllStaff() {
        return userRepository.getAllStaff();
    }

    public boolean addStudent(User student) {
        return userRepository.addStudent(student);
    }

    public boolean addStaff(User staff) {
        return userRepository.addStaff(staff);
    }

    /**
     * checks every text field in the add student page window for invalid or empty entry and prints the appropriate error message
     * to the text field in red color. If the text field already has an error message in red color, on a new entry attempt,
     * it reverses the color back to black
     * @return true or false
     */
    public boolean validateStudentDetails(String librayId, TextField matricNo, TextField firstName, TextField lastName, String imagePath, TextField faculty,
                                           TextField department, TextField level, TextField phoneNo, TextField email) {

        if(validateUserLibraryId(librayId) && validateUserId(matricNo.getText()) && validateFirstName(firstName.getText()) && validateLastName(lastName.getText()) &&
                validateImageUpload(imagePath) && validateFaculty(faculty.getText()) && validateDepartment(department.getText()) && validateLevel(level.getText()) &&
                validatePhoneNumber(phoneNo.getText()) && validateEmail(email.getText())) {
            return true;
        }
        UtilityMethods utilityMethods = new UtilityMethods();

        if (!validateUserId(matricNo.getText()))
            utilityMethods.setTextFieldInvalidAttribute(matricNo);
        else if (!matricNo.getText().equals(addUserPageTextFieldsErrorMessage))
            utilityMethods.reverseTextFieldColor(matricNo);

        if (!validateFirstName(firstName.getText()))
            utilityMethods.setTextFieldInvalidAttribute(firstName);
        else if (!firstName.getText().equals(addUserPageTextFieldsErrorMessage))
            utilityMethods.reverseTextFieldColor(firstName);

        if (!validateLastName(lastName.getText()))
            utilityMethods.setTextFieldInvalidAttribute(lastName);
        else if (!lastName.getText().equals(addUserPageTextFieldsErrorMessage))
            utilityMethods.reverseTextFieldColor(lastName);

        if (!validateFaculty(faculty.getText()))
            utilityMethods.setTextFieldInvalidAttribute(faculty);
        else if (!faculty.getText().equals(addUserPageTextFieldsErrorMessage))
            utilityMethods.reverseTextFieldColor(faculty);

        if (!validateDepartment(department.getText()))
            utilityMethods.setTextFieldInvalidAttribute(department);
        else if (!department.getText().equals(addUserPageTextFieldsErrorMessage))
            utilityMethods.reverseTextFieldColor(department);

        if (!validateLevel(level.getText()))
            utilityMethods.setTextFieldInvalidAttribute(level);
        else if (!level.getText().equals(addUserPageTextFieldsErrorMessage))
            utilityMethods.reverseTextFieldColor(level);

        if (!validatePhoneNumber(phoneNo.getText()))
            utilityMethods.setTextFieldInvalidAttribute(phoneNo);
        else if (!phoneNo.getText().equals(addUserPageTextFieldsErrorMessage))
            utilityMethods.reverseTextFieldColor(phoneNo);

        if (!validateEmail(email.getText()))
            utilityMethods.setTextFieldInvalidAttribute(email);
        else if (!email.getText().equals(addUserPageTextFieldsErrorMessage))
            utilityMethods.reverseTextFieldColor(email);

        return false;
    }

    /**
     * checks every text field in the add staff page window for invalid or empty entry and prints the appropriate error message
     * to the text field in red color. If the text field already has an error message in red color, on a new entry attempt,
     * it reverses the color back to black
     * @return true or false
     */
    public boolean validateStaffDetails(String librayId, TextField staffId, TextField firstName, TextField lastName, String imagePath, TextField faculty,
                                          TextField department, TextField phoneNo, TextField email) {

        if(validateUserLibraryId(librayId) && validateUserId(staffId.getText()) && validateFirstName(firstName.getText()) && validateLastName(lastName.getText()) &&
                validateImageUpload(imagePath) && validateFaculty(faculty.getText()) && validateDepartment(department.getText()) && validatePhoneNumber(phoneNo.getText()) &&
                validateEmail(email.getText())) {
            return true;
        }
        UtilityMethods utilityMethods = new UtilityMethods();

        if (!validateUserId(staffId.getText()))
            utilityMethods.setTextFieldInvalidAttribute(staffId);
        else if (!staffId.getText().equals(addUserPageTextFieldsErrorMessage))
            utilityMethods.reverseTextFieldColor(staffId);

        if (!validateFirstName(firstName.getText()))
            utilityMethods.setTextFieldInvalidAttribute(firstName);
        else if (!firstName.getText().equals(addUserPageTextFieldsErrorMessage))
            utilityMethods.reverseTextFieldColor(firstName);

        if (!validateLastName(lastName.getText()))
            utilityMethods.setTextFieldInvalidAttribute(lastName);
        else if (!lastName.getText().equals(addUserPageTextFieldsErrorMessage))
            utilityMethods.reverseTextFieldColor(lastName);

        if (!validateFaculty(faculty.getText()))
            utilityMethods.setTextFieldInvalidAttribute(faculty);
        else if (!faculty.getText().equals(addUserPageTextFieldsErrorMessage))
            utilityMethods.reverseTextFieldColor(faculty);

        if (!validateDepartment(department.getText()))
            utilityMethods.setTextFieldInvalidAttribute(department);
        else if (!department.getText().equals(addUserPageTextFieldsErrorMessage))
            utilityMethods.reverseTextFieldColor(department);

        if (!validatePhoneNumber(phoneNo.getText()))
            utilityMethods.setTextFieldInvalidAttribute(phoneNo);
        else if (!phoneNo.getText().equals(addUserPageTextFieldsErrorMessage))
            utilityMethods.reverseTextFieldColor(phoneNo);

        if (!validateEmail(email.getText()))
            utilityMethods.setTextFieldInvalidAttribute(email);
        else if (!email.getText().equals(addUserPageTextFieldsErrorMessage))
            utilityMethods.reverseTextFieldColor(email);

        return false;
    }

    public boolean validateUserId(String userId) {
        if(userId.isEmpty() || userId.equals(addUserPageTextFieldsErrorMessage)) {
            System.out.println("user id not validated");
            return false;
        }
        return true;
    }

    public boolean validateFirstName(String firstName) {
        if(firstName.isEmpty() || firstName.equals(addUserPageTextFieldsErrorMessage)) {
            System.out.println("first name not validated");
            return false;
        }
        return true;
    }

    public boolean validateLastName(String lastName) {
        if(lastName.isEmpty() || lastName.equals(addUserPageTextFieldsErrorMessage)) {
            System.out.println("last name not validated");
            return false;
        }
        return true;
    }

    public boolean validateFaculty(String faculty) {
        if(faculty.isEmpty() || faculty.equals(addUserPageTextFieldsErrorMessage)) {
            System.out.println("faculty not validated");
            return false;
        }
        return true;
    }

    public boolean validateDepartment(String department) {
        if(department.isEmpty() || department.equals(addUserPageTextFieldsErrorMessage)) {
            System.out.println("department not validated");
            return false;
        }
        return true;
    }

    public boolean validateLevel(String level) {
        if(level.isEmpty() || level.equals(addUserPageTextFieldsErrorMessage)) {
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
