package com.themaestrocode.aaualms;

import com.themaestrocode.aaualms.service.PasswordService;
import com.themaestrocode.aaualms.utility.PasswordHasher;
import com.themaestrocode.aaualms.utility.UtilityMethods;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ResetPasswordPageController {

    @FXML
    private TextField newPasswordTextField, confirmPasswordTextField;
    private Stage resetPasswordStage;


    public void confirmPasswordReset() {
        UtilityMethods utilityMethods = new UtilityMethods();

        try {
            if(newPasswordTextField.getText().equals(confirmPasswordTextField.getText())) {
                boolean proceed = utilityMethods.showConfirmationAlert("Confirmation Alert", "Confirm the new access code", "Are you sure you want to reset the access code?");

                if(proceed) {
                    PasswordHasher passwordHasher = new PasswordHasher();

                    String salt = passwordHasher.generateSalt(); //generate salt
                    String hashedPassword = passwordHasher.hashPassword(confirmPasswordTextField.getText(), salt); //hash the new password

                    PasswordService passwordService = new PasswordService();

                     if(passwordService.savePassword(hashedPassword, salt)) {
                         utilityMethods.showInformationAlert("Notification", "Access code changed!", "You have successfully changed the access code. Ensure to keep it safe.");
                         resetPasswordStage.close();
                     }
                }
            }
            else {
                utilityMethods.showErrorAlert("Passwords do not match", "The passwords you've entered do not match. Check and try again.");
            }
        }
        catch (Exception e) {
            utilityMethods.showErrorAlert("Password reset failed!", "The password could not be changed.");
        }
    }

    public Stage getResetPasswordStage() {
        return resetPasswordStage;
    }

    public void setResetPasswordStage(Stage resetPasswordStage) {
        this.resetPasswordStage = resetPasswordStage;
    }
}
