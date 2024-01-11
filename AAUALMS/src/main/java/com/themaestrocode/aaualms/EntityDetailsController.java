package com.themaestrocode.aaualms;

import com.themaestrocode.aaualms.entity.Book;
import com.themaestrocode.aaualms.entity.User;
import com.themaestrocode.aaualms.utility.UtilityMethods;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class EntityDetailsController implements Initializable {

    @FXML
    private ImageView userImage;
    @FXML
    private Label label1Value, label2Value, label3Value, label4Value, label5Value, label6Value, label7Value, label8Value;
    @FXML
    private Label label1, label2, label3, label4, label5, label6, label7, label8;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void studentData(User student) {
        UtilityMethods utilityMethods = new UtilityMethods();

        File imageFile = utilityMethods.normalizeFile(student.getImagePath());

        if(imageFile != null && imageFile.exists()) {
            try {
                Image image = new Image(imageFile.toURI().toString());
                userImage.setImage(image);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        label1.setText("Student Lib ID:");
        label2.setText("Name:");
        label3.setText("Matric no:");
        label4.setText("Faculty:");
        label5.setText("Department:");
        label6.setText("Phone No:");
        label7.setText("Email Address:");
        label8.setText("Level:");

        label1Value.setText(student.getUserLibraryId());
        label2Value.setText(student.getFirstName() + " " + student.getLastName());
        label3Value.setText(student.getUserId());
        label4Value.setText(student.getFaculty());
        label5Value.setText(student.getDepartment());
        label6Value.setText(student.getPhoneNumber());
        label7Value.setText(student.getEmail());
        label8Value.setText(student.getLevel());
    }

    public void staffData(User user) {
        UtilityMethods utilityMethods = new UtilityMethods();

        File imageFile = utilityMethods.normalizeFile(user.getImagePath());

        if(imageFile != null && imageFile.exists()) {
            try {
                Image image = new Image(imageFile.toURI().toString());
                userImage.setImage(image);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        label1.setText("Staff Lib ID:");
        label2.setText("Name:");
        label3.setText("Staff ID:");
        label4.setText("Faculty:");
        label5.setText("Department:");
        label6.setText("Phone No:");
        label7.setText("Email Address:");
        label8.setVisible(false);

        label1Value.setText(user.getUserLibraryId());
        label2Value.setText(user.getFirstName() + " " + user.getLastName());
        label3Value.setText(user.getUserId());
        label4Value.setText(user.getFaculty());
        label5Value.setText(user.getDepartment());
        label6Value.setText(user.getPhoneNumber());
        label7Value.setText(user.getEmail());
        label8Value.setVisible(false);
    }

//    public void bookData(Book book) {
//        UtilityMethods utilityMethods = new UtilityMethods();
//
//        File imageFile = utilityMethods.normalizeFile(book.getImagePath());
//
//        if(imageFile != null && imageFile.exists()) {
//            try {
//                Image image = new Image(imageFile.toURI().toString());
//                userImage.setImage(image);
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        label1.setText("Staff Lib ID:");
//        label2.setText("Name:");
//        label3.setText("Staff ID:");
//        label4.setText("Faculty:");
//        label5.setText("Department:");
//        label6.setText("Phone Number:");
//        label7.setText("Email Address:");
//        label8.setVisible(false);
//
//        label1Value.setText(book.getUserLibraryId());
//        label2Value.setText(book.getFirstName() + " " + book.getLastName());
//        label3Value.setText(book.getUserId());
//        label4Value.setText(book.getFaculty());
//        label5Value.setText(book.getDepartment());
//        label6Value.setText(book.getPhoneNumber());
//        label7Value.setText(book.getEmail());
//        label8Value.setVisible(false);
//    }
}
