package com.themaestrocode.aaualms.entity;

import javafx.scene.control.TextField;

public class User {

    private TextField userLibraryId;
    private TextField userId;
    private TextField firstName;
    private TextField lastName;
    private TextField imagePath;
    private TextField department;
    private TextField level;
    private TextField phoneNumber;
    private TextField email;

    //constructor for student user
    public User(TextField userLibraryId, TextField userId, TextField firstName, TextField lastName, TextField imagePath, TextField department, TextField level, TextField phoneNumber, TextField email) {
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

    //Constructor for staff user
    public User(TextField userLibraryId, TextField userId, TextField firstName, TextField lastName, TextField imagePath, TextField department, TextField phoneNumber, TextField email) {
        this.userLibraryId = userLibraryId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imagePath = imagePath;
        this.department = department;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
