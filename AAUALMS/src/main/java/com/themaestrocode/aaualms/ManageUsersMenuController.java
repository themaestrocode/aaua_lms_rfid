package com.themaestrocode.aaualms;

import com.themaestrocode.aaualms.service.DepartmentService;
import com.themaestrocode.aaualms.service.FacultyService;
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
    private Label userIdLabel, photoUploadConfirmationLabel;
    @FXML
    private Label scanCardConfirmationLabel;
    @FXML
    private TextField facultyTextField, departmentTextField, levelTextField;
    @FXML
    private ComboBox facultyComboBox, departmentComboBox, levelComboBox;
    @FXML
    private RadioButton studentRadioButton, staffRadioButton;

    private Stage stage;

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

        File selectedFile = fileChooser.showOpenDialog(this.getStage());

        if(selectedFile != null) {
            System.out.println(selectedFile.getAbsoluteFile());
            photoUploadConfirmationLabel.setText("File uploaded!");
            return selectedFile;
        }
        return null;
    }

    public void scanCard() throws IOException {
        ScannerWindowController scannerWindowController = new ScannerWindowController();

        scannerWindowController.loadScannerWindow();
        scannerWindowController.validateCardOrTag("user");
    }

    public void changeButtonColor() {
        saveUserButton.setStyle("-fx-background-color: #113C14");
    }

    public void reverseButtonColor() {
        saveUserButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #006400, #32CD32)");
    }

    public String getSaveUserButtonText() {
        return saveUserButton.getText();
    }

    public void setScanCardConfirmationLabelAttribute(String color, String text) {
        scanCardConfirmationLabel.setText(text);
        scanCardConfirmationLabel.setStyle(color);
    }
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
