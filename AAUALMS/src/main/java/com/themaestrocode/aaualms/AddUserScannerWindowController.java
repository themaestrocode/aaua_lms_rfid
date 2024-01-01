package com.themaestrocode.aaualms;

import com.themaestrocode.aaualms.service.UserService;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
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

public class AddUserScannerWindowController implements Initializable {

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

        validateCard("user");
    }

    public void loadScannerWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("scannerWindow.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        Image scanIcon = new Image(getClass().getResourceAsStream("/com/themaestrocode/images/scan icon.png"));

        scannerStage = new Stage();
        scannerStage.initModality(Modality.APPLICATION_MODAL);
        scannerStage.setTitle("Scanner Window");
        scannerStage.setScene(scene);
        scannerStage.getIcons().add(scanIcon);
        scannerStage.setResizable(false);
        scannerStage.show();
    }

    public void validateCard(String userOrBook) {
        String result = null;
        // Add a listener to perform checks when the text length reaches 10 characters
        cardOrTagValueTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.length() == 10) {
                // Simulate database check (Replace this with your database query)

                boolean found = false;

                if(userOrBook.equals("user")) {
                    UserService userService = new UserService();
                    found = userService.findUser(newValue);
                }

                if (found) {
                    Platform.runLater(() -> {
                        cardOrTagValueTextField.clear();
                        System.out.println("id already registered!");
                    });
                } else {
                    // Optional: Inform the user or perform actions for a valid entry
                    cardOrTagValueTextField.setText(newValue);
                    System.out.println("id not yet registered: " + newValue);

                    if(scannerStage != null) {
                        this..close();
                    }
                }
            }
        });
    }

    public void displayCardOrTagMessage() {

    }

    public Stage getScannerStage() {
        return scannerStage;
    }

    public void setScannerStage(Stage scannerStage) {
        this.scannerStage = scannerStage;
    }
}
