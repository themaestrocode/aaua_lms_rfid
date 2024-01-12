package com.themaestrocode.aaualms;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddBookPageController implements Initializable {

    @FXML
    private Button addBookButton;
    @FXML
    private ImageView scanIcon;
    @FXML
    private Label photoUploadConfirmationLabel, scanTagConfirmationLabel, addBookOutcomeLabel;
    @FXML
    private TextField bookIdTextField, titleTextField, authorTextField, shelveNoTextField, isbnTextField, publisherTextField;

    private Stage addBookStage;
    private String bookId, imagePath;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scanIcon.setVisible(false);

    }

    public void changeAddBookButtonColor() {
        addBookButton.setStyle("-fx-background-color: #113C14");
    }

    public void reverseAddBookButtonColor() {
        addBookButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #006400, #32CD32)");
    }

    public Stage getAddBookStage() {
        return addBookStage;
    }

    public void setAddBookStage(Stage addBookStage) {
        this.addBookStage = addBookStage;
    }
}
