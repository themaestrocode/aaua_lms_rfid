package com.themaestrocode.aaualms;

import com.themaestrocode.aaualms.model.DBConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class LoginPageController {

    @FXML
    TextField accessCodeField;
    @FXML
    Label errorMessageID;
    @FXML
    private Button loginButton;

    private final String CODE = "lib1234";
    private final int MAX_ATTEMPTS = 5;
    private int attempts = 0;

    private MainPageController mainPageController;

    public void login(ActionEvent event) throws IOException {
        String accessCode = accessCodeField.getText();

        mainPageController = new MainPageController();

        if(accessCode.equals(CODE)) {
            try {
                mainPageController.loadMainPage(event);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            attempts++;
            if(attempts < MAX_ATTEMPTS) {
                errorMessageID.setText("Incorrect access code! Please check and try again.");
            }
            else {
                errorMessageID.setText("Too many incorrect attempts! Wait for 30 seconds");
                loginButton.setDisable(true);
                scheduleButtonEnable(30000);
            }
        }
    }

    public void loadLoginPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("loginPageScene.fxml"));
        ((Node) event.getSource()).getScene().setRoot(root);
    }

    public void changeButtonColor() {
        loginButton.setStyle("-fx-background-color: #113C14");
        fetchDataFromDatabase();
    }

    public void reverseButtonColor() {
        loginButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #006400, #32CD32)");
    }

    private void scheduleButtonEnable(int delayMillis) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Enable the button after the delay
                loginButton.setDisable(false);
            }
        }, delayMillis);
    }

    public void fetchDataFromDatabase() {
        try {
            Connection connection = DBConnector.connect();
            // Perform database operations like executing queries, updates, etc.

            // Example: Select data
            String query = "SELECT * FROM department";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Process the ResultSet
            while (resultSet.next()) {
                // Retrieve data from the result set
                String columnValue = resultSet.getString("department_name");
                System.out.println(columnValue);
                // Process retrieved data
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }
}
