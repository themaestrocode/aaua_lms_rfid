package com.themaestrocode.aaualms;

import com.themaestrocode.aaualms.entity.Password;
import com.themaestrocode.aaualms.service.PasswordService;
import com.themaestrocode.aaualms.utility.UtilityMethods;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class SettingsPageController {

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
}
