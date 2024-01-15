package com.themaestrocode.aaualms;

import com.themaestrocode.aaualms.entity.Book;
import com.themaestrocode.aaualms.entity.User;

import com.themaestrocode.aaualms.service.BookService;
import com.themaestrocode.aaualms.service.UserService;
import com.themaestrocode.aaualms.utility.UtilityMethods;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ScannerWindowController implements Initializable {

    @FXML
    private ImageView scannerImage;
    @FXML
    private TextField scannerWindowTextField;
    private Stage scannerWindowStage;
    private String userLibraryId;
    private User user = null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UtilityMethods utilityMethods = new UtilityMethods();

        utilityMethods.setScanIconAnimation(scannerImage, true);

//        if(!scannerWindowTextField.getText().isEmpty()) {
//            utilityMethods.setScanIconAnimation(scannerImage, false);
//        }

        performScanningOperationForBookIssue();
    }

    public void check() {
        UserService userService = new UserService();

        String a = scannerWindowTextField.getText();
        User user = userService.findUserByLibraryId(a);

        if(user != null) {
            userLibraryId = a;
        }
    }

    public void performScanningOperationForBookIssue() {
        BookService bookService = new BookService();
        UserService userService = new UserService();

        //scannerWindowTextField.requestFocus();

//        scannerWindowTextField.textProperty().addListener((observable, oldValue, newValue) -> {
//            if(newValue != null && newValue.length() == 10) {
//                user = userService.findUserByLibraryId(newValue);
//
//                if(user != null) {
//                    userLibraryId = newValue;
//                    System.out.println("checking userId in ScannerWindowController: " + user);
//                    scannerWindowStage.close();
//                    userLibraryId = newValue;
//                }
//
////                if(user != null) {
////                    userLibraryId = newValue;
////                    Platform.runLater(() -> {
////                        System.out.println("checking userId in ScannerWindowController: " + userLibraryId);
////                        scannerWindowStage.close();
////                    });
////                }
////                else {
////                    UtilityMethods utilityMethods = new UtilityMethods();
////                    utilityMethods.setScanIconAnimation(scannerImage, false);
////                    utilityMethods.showInformationAlert("Error", "User not found", "This person has not been registered as a patron!");
////                    scannerWindowStage.close();
////                }
//            }
//        });
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserLibraryId() {
        return userLibraryId;
    }

    public void setUserLibraryId(String userLibraryId) {
        this.userLibraryId = userLibraryId;
    }

    public TextField getScannerWindowTextField() {
        return scannerWindowTextField;
    }

    public void setScannerWindowTextField(TextField scannerWindowTextField) {
        this.scannerWindowTextField = scannerWindowTextField;
    }

    public Stage getScannerWindowStage() {
        return scannerWindowStage;
    }

    public void setScannerWindowStage(Stage scannerWindowStage) {
        this.scannerWindowStage = scannerWindowStage;
    }
}
