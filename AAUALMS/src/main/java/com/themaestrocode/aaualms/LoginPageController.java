package com.themaestrocode.aaualms;

import com.themaestrocode.aaualms.entity.Password;
import com.themaestrocode.aaualms.service.PasswordService;
import com.themaestrocode.aaualms.utility.PasswordHasher;
import com.themaestrocode.aaualms.utility.UtilityMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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

    private final int MAX_ATTEMPTS = 5;
    private int attempts = 0;

    private UtilityMethods utilityMethods = new UtilityMethods();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
    public void login(ActionEvent event) {
        String accessCode = accessCodeField.getText();

        PasswordHasher passwordHasher = new PasswordHasher();
        boolean isValid = passwordHasher.verifyPassword(accessCode);

        if(isValid) {
            try {
                DashboardController dashboardController = new DashboardController();
                dashboardController.loadDashboard(event);
            } catch (Exception e) {
                utilityMethods.showErrorAlert("Failed to load page!", "The requested page could not be loaded.");
            }
        } else {
            attempts++;
            if(attempts < MAX_ATTEMPTS) {
                errorMessageID.setText("Incorrect access code! Please check and try again.");
            } else {
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

    public void resetPassword() {
        UtilityMethods utilityMethods = new UtilityMethods();

        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Notification");
            dialog.setHeaderText("Enter any past password you remember");
            dialog.setContentText("To reset the access code, you need to provide any previous password: ");

            // Show the dialog and wait for the user's response
            Optional<String> result = dialog.showAndWait();

            // Process the user's response
            result.ifPresent(providedPassword -> {
                try {
                    PasswordService passwordService = new PasswordService();
                    Password password = passwordService.findPassword(providedPassword);

                    if(password != null) {
                        loadResetPasswordPage();
                    } else {
                        utilityMethods.showErrorAlert("Password not found!", "The password you have entered does not match any previous password.");
                    }
                } catch (Exception e) {
                    utilityMethods.showErrorAlert("Failed to load page!", "The requested page could not be loaded.");
                }
            });
        } catch (Exception e) {
            utilityMethods.showErrorAlert("An error has occurred", "Something went wrong.");
        }
    }

    public void loadResetPasswordPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resetPasswordPage.fxml"));
        Parent root = fxmlLoader.load();

        ResetPasswordPageController resetPasswordPageController = fxmlLoader.getController();
        Stage resetPasswordStage = new Stage();
        resetPasswordPageController.setResetPasswordStage(resetPasswordStage);

        Scene scene = new Scene(root);

        String css = this.getClass().getResource("/com/themaestrocode/css/styling.css").toExternalForm();
        scene.getStylesheets().add(css);

        Image passwordIcon = new Image(getClass().getResourceAsStream("/com/themaestrocode/images/key.png"));

        resetPasswordStage.initModality(Modality.APPLICATION_MODAL);
        resetPasswordStage.setTitle("Reset Password");
        resetPasswordStage.setScene(scene);
        resetPasswordStage.getIcons().add(passwordIcon);
        resetPasswordStage.setResizable(false);
        resetPasswordStage.show();
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
