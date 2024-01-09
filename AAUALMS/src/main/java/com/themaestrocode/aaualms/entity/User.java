package com.themaestrocode.aaualms.entity;

import javafx.scene.control.TextField;

public class User {

    private String userLibraryId;
    private TextField userId;
    private TextField firstName;
    private TextField lastName;
    private String imagePath;
    private TextField faculty;
    private TextField department;
    private TextField level;
    private TextField phoneNumber;
    private TextField email;

    //constructor for student user
    public User(String userLibraryId, TextField userId, TextField firstName, TextField lastName, String imagePath, TextField faculty, TextField department, TextField level, TextField phoneNumber, TextField email) {
        this.userLibraryId = userLibraryId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imagePath = imagePath;
        this.faculty = faculty;
        this.department = department;
        this.level = level;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    //Constructor for staff user
    public User(String userLibraryId, TextField userId, TextField firstName, TextField lastName, String imagePath, TextField faculty, TextField department, TextField phoneNumber, TextField email) {
        this.userLibraryId = userLibraryId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imagePath = imagePath;
        this.faculty = faculty;
        this.department = department;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getUserLibraryId() {
        return userLibraryId;
    }

    public void setUserLibraryId(String userLibraryId) {
        this.userLibraryId = userLibraryId;
    }

    public TextField getUserId() {
        return userId;
    }

    public void setUserId() {
        this.userId = userId;
    }

    public TextField getFirstName() {
        return firstName;
    }

    public void setFirstName(TextField firstName) {
        this.firstName = firstName;
    }

    public TextField getLastName() {
        return lastName;
    }

    public void setLastName(TextField lastName) {
        this.lastName = lastName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public TextField getFaculty() {
        return faculty;
    }

    public void setFaculty(TextField faculty) {
        this.faculty = faculty;
    }

    public TextField getDepartment() {
        return department;
    }

    public void setDepartment(TextField department) {
        this.department = department;
    }

    public TextField getLevel() {
        return level;
    }

    public void setLevel(TextField level) {
        this.level = level;
    }

    public TextField getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(TextField phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public TextField getEmail() {
        return email;
    }

    public void setEmail(TextField email) {
        this.email = email;
    }
}
