package com.themaestrocode.aaualms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageUsersMenuController implements Initializable {

    @FXML
    private Button saveUserButton;
    @FXML
    private Label userIdLabel;
    @FXML
    private TextField levelTextField;
    @FXML
    private ComboBox levelComboBox;
    @FXML
    private RadioButton studentRadioButton, staffRadioButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        studentRadioButton.setSelected(true);
        userIdLabel.setText("Matric No:");
    }

    /**
     * loads an unresizable stage: "the add user stage and scene" upon the main page and primary stage.
     * @throws IOException
     */
    public void loadAddUserPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addUserPage.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        String css = this.getClass().getResource("/com/themaestrocode/css/styling.css").toExternalForm();
        scene.getStylesheets().add(css);

        Image userImage = new Image(getClass().getResourceAsStream("/com/themaestrocode/images/a user icon.png"));

        Stage addUserStage = new Stage();
        addUserStage.initModality(Modality.APPLICATION_MODAL);
        addUserStage.setTitle("Add User");
        addUserStage.setScene(scene);
        addUserStage.getIcons().add(userImage);
        addUserStage.setResizable(false);
        addUserStage.show();
    }

    /**
     * switches between the studentRadioButton and staffRadioButton, enabling the necessary fields for getting student and staff details
     */
    public void switchAddUserDetails() {
        //getting the selected RadioButton from the toggleGroup
        if(studentRadioButton.isSelected()) {
            userIdLabel.setText("Matric No:");
            levelComboBox.setDisable(false);
            levelTextField.setDisable(false);
        }
        else if(staffRadioButton.isSelected()) {
            userIdLabel.setText("Staff ID:");
            levelComboBox.setDisable(true);
            levelTextField.setDisable(true);
        }
    }

    public void changeButtonColor() {
        saveUserButton.setStyle("-fx-background-color: #113C14");
    }

    public void reverseButtonColor() {
        saveUserButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #006400, #32CD32)");
    }
}
