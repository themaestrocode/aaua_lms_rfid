package com.themaestrocode.aaualms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPageController {

    @FXML
    private Button logoutButton;

    private Parent root;
    private Stage stage;
    private Scene scene;

    private LoginPageController loginPageController;

    public void logout(ActionEvent event) throws IOException {
        Alert logoutAlert = new Alert(Alert.AlertType.CONFIRMATION);
        logoutAlert.setTitle("Notification");
        logoutAlert.setHeaderText("Logout");
        logoutAlert.setContentText("Are you sure you want to logout?");

        if(logoutAlert.showAndWait().get() == ButtonType.OK) {
            try {
                loginPageController = new LoginPageController();
                loginPageController.loadLoginPage(event);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void loadMainPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainPageScene.fxml"));
        ((Node) event.getSource()).getScene().setRoot(root);
    }

    public void changeButtonColor() {
        logoutButton.setStyle("-fx-background-color: #6F1515");
    }

    public void reverseButtonColor() {
        logoutButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #AA0000, #EF0107)");
    }
}
