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
    private Label matricNoLabel;
    @FXML
    private TextField levelTextField;
    @FXML
    private ComboBox levelComboBox;
    @FXML
    private RadioButton studentRadioButton, staffRadioButton;
    private ToggleGroup toggleGroup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toggleGroup = new ToggleGroup();

        studentRadioButton.setToggleGroup(toggleGroup);
        staffRadioButton.setToggleGroup(toggleGroup);

        studentRadioButton.setSelected(true);
    }

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

    public void switchAddUserDetailsToStudent() {
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();

        if(selectedRadioButton != null) {
            String selectedOption = selectedRadioButton.getText();

            if(selectedOption.equals("Student")) {
                matricNoLabel.setText("Matric No:");
                levelComboBox.setDisable(false);
                levelTextField.setDisable(false);
            }
        }
    }

    public void switchAddUserDetailsToStaff() {
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();

        if(selectedRadioButton != null) {
            String selectedOption = selectedRadioButton.getText();

            if(selectedOption.equals("Staff")) {
                matricNoLabel.setText("Staff ID:");
                levelComboBox.setDisable(true);
                levelTextField.setDisable(true);
            }
        }
    }

    public void changeButtonColor() {
        saveUserButton.setStyle("-fx-background-color: #113C14");
    }

    public void reverseButtonColor() {
        saveUserButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #006400, #32CD32)");
    }
}
