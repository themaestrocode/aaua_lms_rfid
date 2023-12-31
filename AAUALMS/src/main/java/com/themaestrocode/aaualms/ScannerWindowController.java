package com.themaestrocode.aaualms;

import com.themaestrocode.aaualms.service.BookService;
import com.themaestrocode.aaualms.service.UserService;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ScannerWindowController implements Initializable {

    private Stage scannerStage;

    @FXML
    private ImageView scanImage;
    @FXML
    private TextField cardOrTagValueTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cardOrTagValueTextField.setVisible(true);

        RotateTransition rotateTransition = new RotateTransition();

        rotateTransition.setNode(scanImage);
        rotateTransition.setDuration(Duration.millis(3000));
        rotateTransition.setCycleCount(TranslateTransition.INDEFINITE);
        rotateTransition.setInterpolator(Interpolator.LINEAR);
        rotateTransition.setByAngle(360);
        rotateTransition.play();

        //validateCardOrTag("user");
    }

    public void loadScannerWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("scannerWindow.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        String css = this.getClass().getResource("/com/themaestrocode/css/styling.css").toExternalForm();
        scene.getStylesheets().add(css);

        Image scanIcon = new Image(getClass().getResourceAsStream("/com/themaestrocode/images/scan icon.png"));

        scannerStage = new Stage();
        scannerStage.initModality(Modality.APPLICATION_MODAL);
        scannerStage.setTitle("Scanner Window");
        scannerStage.setScene(scene);
        scannerStage.getIcons().add(scanIcon);
        scannerStage.setResizable(false);
        scannerStage.show();
    }

    public void validateCardOrTag(String userOrBook) {
        // Add a listener to perform checks when the text length reaches 10 characters
        cardOrTagValueTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.length() == 10) {
                // Simulate database check (Replace this with your database query)

                boolean found = false;

                if(userOrBook.equals("user")) {
                    UserService userService = new UserService();
                    found = userService.findUser(newValue);
                }
                else if(userOrBook.equals("book")) {
                    BookService bookService = new BookService();
                    found = bookService.findBook(newValue);
                }

                if (!found) {
                    Platform.runLater(() -> {
                        cardOrTagValueTextField.clear();
                        System.out.println("id not found!");
                    });
                } else {
                    // Optional: Inform the user or perform actions for a valid entry
                    System.out.println("id found: " + newValue);
                }
            }
        });
    }

    public void displayCardOrTagMessage() {

    }

    private void setupChangeListener(Stage scannerStage) {
        ChangeListener<String> readerListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String cardOrTagValue = cardOrTagValueTextField.getText();

                if (!cardOrTagValue.isEmpty()) {
                    UserService userService = new UserService();
                    ManageUsersMenuController manageUsersMenuController = new ManageUsersMenuController();

                    if (userService.findUser(cardOrTagValue)) {
                        //manageUsersMenuController.getScanCardConfirmationLabel().setText("card already registered to a user!");
                        scannerStage.close();
                        //manageUsersMenuController.setScanCardConfirmationLabelAttribute("-fx-text-fill: #AA0000", "card already registered to a user!");
                    } else {
                        //manageUsersMenuController.getScanCardConfirmationLabel().setText("card successfully scanned!");
                        //manageUsersMenuController.setScanCardConfirmationLabelAttribute("-fx-text-fill: #006400", "card successfully scanned!");
                        scannerStage.close();
                    }
                }
            }
        };
        if(cardOrTagValueTextField != null) {
            cardOrTagValueTextField.textProperty().addListener(readerListener);
        }

    }

    public Stage getScannerStage() {
        return scannerStage;
    }

    public void setScannerStage(Stage scannerStage) {
        this.scannerStage = scannerStage;
    }
}
