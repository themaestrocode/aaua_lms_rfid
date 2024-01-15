package com.themaestrocode.aaualms;

import com.themaestrocode.aaualms.utility.UtilityMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class LoginPageController implements Initializable {

    @FXML
    TextField accessCodeField;
    @FXML
    Label errorMessageID;
    @FXML
    private Button loginButton;

    private final String CODE = "lib1234";
    private final int MAX_ATTEMPTS = 5;
    private int attempts = 0;

    private DashboardController dashboardController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UtilityMethods utilityMethods = new UtilityMethods();

        loginButton.setOnMouseEntered(mouseEvent -> {
            utilityMethods.changeGreenButtonColor(loginButton);
        });

        loginButton.setOnMouseExited(mouseEvent -> {
            utilityMethods.reverseGreenButtonColor(loginButton);
        });
    }

    /**
     * gets an object of the DashboardController Class and opens the dashboard scene.
     * the login button is disabled for 30 secs after a 5 time incorrect login attempt is made.
     * @param event
     * @throws IOException
     */
    public void login(ActionEvent event) throws IOException {
        String accessCode = accessCodeField.getText();

        dashboardController = new DashboardController();

        if(accessCode.equals(CODE)) {
            try {
                dashboardController.loadDashboard(event);
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

    /**
     * loads the login page scene unto the primary stage.
     * Can be called by any class from where access to the login page is needed.
     * @param event
     * @throws IOException
     */
    public void loadLoginPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("loginPageScene.fxml"));
        ((Node) event.getSource()).getScene().setRoot(root);
    }

    /**
     * changes the login button color to a darker shade of green when the mouse hovers over it
     */
    public void changeButtonColor() {
        loginButton.setStyle("-fx-background-color: #113C14");
    }

    /**
     * reverses the color of the login button after the mouse has been moved away
     */
    public void reverseButtonColor() {
        loginButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #006400, #32CD32)");
    }

    /**
     * disables the login button for a specified time @delayMillis after 5 incorrect attempts.
     * @param delayMillis
     */
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
}
