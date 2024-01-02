package com.themaestrocode.aaualms;

import com.themaestrocode.aaualms.service.DepartmentService;
import com.themaestrocode.aaualms.service.FacultyService;
import com.themaestrocode.aaualms.service.UserService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageUsersMenuController implements Initializable {

    @FXML
    private Button saveUserButton;
    @FXML
    private Label userIdLabel, photoUploadConfirmationLabel, scanCardConfirmationLabel;
    @FXML
    private TextField userIdTextField, firstNameTextField, lastNameTextField, facultyTextField, departmentTextField, levelTextField, phoneNoTextField, emailTextField, scanCardTextField;
    @FXML
    private ComboBox facultyComboBox, departmentComboBox, levelComboBox;
    @FXML
    private RadioButton studentRadioButton, staffRadioButton;

    private Stage addUserStage = new Stage();
    private String cardNo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FacultyService facultyService = new FacultyService();

        studentRadioButton.setSelected(true);
        userIdLabel.setText("Matric No:");

        facultyComboBox.setItems(facultyService.getAllFaculty());
        facultyComboBox.setEditable(false);
        levelComboBox.getItems().addAll("100L", "200L", "300L", "400L", "500L", "PGD");
        departmentComboBox.setEditable(false);
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

        //addUserStage = new Stage();
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

    public void chooseFaculty() {
        DepartmentService departmentService = new DepartmentService();

        String selectedFaculty = (String) facultyComboBox.getValue();
        facultyTextField.setText(selectedFaculty);

        departmentComboBox.setItems(departmentService.getFacultiesUnderSelectedDepartment(selectedFaculty));
    }

    public void chooseDepartment() {
        String selectedDepartment = (String) departmentComboBox.getValue();
        departmentTextField.setText(selectedDepartment);
    }

    public void chooseLevel() {
        String selectedLevel = (String) levelComboBox.getValue();
        levelTextField.setText(selectedLevel);
    }

    public File uploadPhoto() {
        FileChooser fileChooser = new FileChooser();

        //title for the fileChooser dialog
        fileChooser.setTitle("Upload User Photo");

        //choosing the initial directory where the file chooser opens by default
        File initialDirectory = new File("C:\\Users\\user\\Documents\\My Files\\AAUA LMS users photo album");
        fileChooser.setInitialDirectory(initialDirectory);

        //showing the file chooser dialog
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG files", "*.jpg")
        );

        File selectedFile = fileChooser.showOpenDialog(this.getAddUserStage());

        if(selectedFile != null) {
            System.out.println(selectedFile.getAbsoluteFile());
            photoUploadConfirmationLabel.setText("File uploaded!");
            setScanCardConfirmationLabelAttribute("-fx-text-fill: black", "waiting for user to scan card...");
            return selectedFile;
        }
        return null;
    }

    public void scanCard() {
        scanCardTextField.requestFocus();
        scanCardTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.length() == 10) {
                // Simulate database check (Replace this with your database query)

                boolean userFound = false;

                UserService userService = new UserService();
                userFound = userService.findUser(newValue);

                if (userFound) {
                    Platform.runLater(() -> {
                        scanCardTextField.clear();
                        System.out.println("id already registered!");
                        scanCardConfirmationLabel.setText("");
                        showAlert("Notification: Card", "This card has already been registered with another user!");
                    });
                } else {
                    // Optional: Inform the user or perform actions for a valid entry
                    scanCardTextField.setText(newValue);
                    scanCardConfirmationLabel.requestFocus();
                    System.out.println("id not yet registered: " + newValue);
                    setScanCardConfirmationLabelAttribute("-fx-text-fill: #006400", "card successfully scanned!");
                }
            }
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public String getId() {
        System.out.println(scanCardTextField.getText());
        return scanCardTextField.getText();
    }

    private void loadScannerWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("scannerWindow.fxml"));
        Parent scannerWindowRoot = fxmlLoader.load();
        Scene scannerWindowScene = new Scene(scannerWindowRoot);

        Image scanIcon = new Image(getClass().getResourceAsStream("/com/themaestrocode/images/scan icon.png"));

        getAddUserStage().initModality(Modality.APPLICATION_MODAL);
        getAddUserStage().setScene(scannerWindowScene);
        getAddUserStage().setTitle("Scanner Window");
        getAddUserStage().getIcons().add(scanIcon);
        getAddUserStage().setResizable(false);
        getAddUserStage().show();
    }

    public void changeButtonColor() {
        saveUserButton.setStyle("-fx-background-color: #113C14");
    }

    public void reverseButtonColor() {
        saveUserButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #006400, #32CD32)");
    }

    public void setScanCardConfirmationLabelAttribute(String color, String text) {
        scanCardConfirmationLabel.setStyle(color);
        scanCardConfirmationLabel.setText(text);
    }
    public Stage getAddUserStage() {
        return addUserStage;
    }

    public void setAddUserStage(Stage addUserStage) {
        this.addUserStage = addUserStage;
    }

}
