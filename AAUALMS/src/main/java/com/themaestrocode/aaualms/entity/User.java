package com.themaestrocode.aaualms.entity;

public class User {

    private String userLibraryId;
    private String userId;
    private String firstName;
    private String lastName;
    private String imagePath;
    private Department department;
    private String level;
    private String phoneNumber;
    private String email;

    //constructor for student user
    public User(String userLibraryId, String userId, String firstName, String lastName, String imagePath, Department department, String level, String phoneNumber, String email) {
        this.userLibraryId = userLibraryId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imagePath = imagePath;
        this.department = department;
        this.level = level;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    //constructor for staff user
    public User(String userLibraryId, String userId, String firstName, String lastName, String imagePath, Department department, String phoneNumber, String email) {
        this.userLibraryId = userLibraryId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imagePath = imagePath;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
