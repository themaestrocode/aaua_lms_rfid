package com.themaestrocode.aaualms;

import com.themaestrocode.aaualms.entity.User;
import com.themaestrocode.aaualms.service.DepartmentService;
import com.themaestrocode.aaualms.service.FacultyService;
import com.themaestrocode.aaualms.service.UserService;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageUsersMenuController implements Initializable {

    @FXML
    private Button saveUserButton;
    @FXML
    private Label userIdLabel, photoUploadConfirmationLabel, scanCardConfirmationLabel, saveUserOutcomeLabel;
    @FXML
    private TextField userLibraryIdTextField, userIdTextField, firstNameTextField, lastNameTextField, facultyTextField, departmentTextField, levelTextField, phoneNoTextField, emailTextField;
    @FXML
    private ComboBox facultyComboBox, departmentComboBox, levelComboBox;
    @FXML
    private RadioButton studentRadioButton, staffRadioButton;
    @FXML
    private ImageView scanIcon;

    private Stage addUserStage = new Stage();
    private String cardNo;
    private String imagePath;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FacultyService facultyService = new FacultyService();

        //sets the studentRadioButton as the default option when the addUser scene appears
        studentRadioButton.setSelected(true);
        userIdLabel.setText("Matric No:");

        //fetch all faculties from the DB and populate the faculty combo box
        facultyComboBox.setItems(facultyService.getAllFaculty());
        facultyComboBox.setEditable(false);
        levelComboBox.getItems().addAll("100L", "200L", "300L", "400L", "500L", "PGD");
        departmentComboBox.setEditable(false);
        userLibraryIdTextField.setVisible(false);
        scanIcon.setVisible(false);
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

    /**
     * called when a faculty gets selected from the faculty Combobox item list.
     * It also populates the department combo box based on the selectd faculty
     */
    public void chooseFaculty() {
        DepartmentService departmentService = new DepartmentService();

        String selectedFaculty = (String) facultyComboBox.getValue();
        facultyTextField.setText(selectedFaculty);

        departmentComboBox.setItems(departmentService.getFacultiesUnderSelectedDepartment(selectedFaculty));
    }

    /**
     * called when a department gets selected from the department Combobox item list.
     */
    public void chooseDepartment() {
        String selectedDepartment = (String) departmentComboBox.getValue();
        departmentTextField.setText(selectedDepartment);
    }

    /**
     * called when a level gets selected from the level Combobox item list.
     */
    public void chooseLevel() {
        String selectedLevel = (String) levelComboBox.getValue();
        levelTextField.setText(selectedLevel);
    }

    /**
     * called by the uploadPhotoButton: to choose the photo of the user from the system
     * @return
     */
    public File uploadPhoto() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Upload User Photo");

        //setting the initial directory where the file chooser opens by default
        File initialDirectory = new File("C:\\Users\\user\\Documents\\My Files\\AAUA LMS users photo album");
        fileChooser.setInitialDirectory(initialDirectory);

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG files", "*.jpg")
        );

        File selectedFile = fileChooser.showOpenDialog(this.getAddUserStage());

        if(selectedFile != null) {
            imagePath = selectedFile.getAbsolutePath();
            System.out.println(imagePath);
            photoUploadConfirmationLabel.setText("Photo uploaded!");
            setLabelAttribute(scanCardConfirmationLabel, "-fx-text-fill: black", "waiting for user to scan card...");
            return selectedFile;
        }
        return null;
    }

    public void scanCard() {
        if(!validateImageUpload(imagePath)) {
            setLabelAttribute(photoUploadConfirmationLabel, "-fx-text-fill: #AA0000", "Photo not uploaded!");
        }

        setScanIconAnimation(true);

        userLibraryIdTextField.requestFocus();
        userLibraryIdTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.length() == 10) {
                // Simulate database check (Replace this with your database query)

                boolean userFound = false;

                UserService userService = new UserService();
                userFound = userService.findUser(newValue);

                if (userFound) {
                    Platform.runLater(() -> {
                        userLibraryIdTextField.clear();
                        System.out.println("id already registered!");
                        scanCardConfirmationLabel.setText("");
                        showAlert("Notification: Card", "This card has already been registered with another user!");
                    });
                } else {
                    // Optional: Inform the user or perform actions for a valid entry
                    setScanIconAnimation(false);
                    userLibraryIdTextField.setText(newValue);
                    scanCardConfirmationLabel.requestFocus();
                    System.out.println("id not yet registered: " + newValue);
                    setLabelAttribute(scanCardConfirmationLabel, "-fx-text-fill: #006400", "card successfully scanned!");
                }
            }
        });
    }

    public void saveUser() {
        UserService userService = new UserService();

        boolean userSaved = false;

        if(studentRadioButton.isSelected()) {
            User student = new User(userLibraryIdTextField, userIdTextField, firstNameTextField, lastNameTextField, imagePath, facultyTextField, departmentTextField,
                    levelTextField, phoneNoTextField, emailTextField);
            userSaved = userService.saveStudent(student);
        }
        else if(staffRadioButton.isSelected()) {
            User staff = new User(userLibraryIdTextField, userIdTextField, firstNameTextField, lastNameTextField, imagePath, facultyTextField, departmentTextField,
                    phoneNoTextField, emailTextField);
            userSaved = userService.saveStaff(staff);
        }

        if(userSaved) {
            if(!saveUserOutcomeLabel.getText().isEmpty()) {
                saveUserOutcomeLabel.setText("");
            }
            showAlert("Notification: User", "User successfully added as a patron!");
            getAddUserStage().close();
        }
        else {
            setLabelAttribute(saveUserOutcomeLabel, "-fx-text-fill: #AA0000", "error: user could not be saved!");
        }

    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

//    private void loadScannerWindow() throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("scannerWindow.fxml"));
//        Parent scannerWindowRoot = fxmlLoader.load();
//        Scene scannerWindowScene = new Scene(scannerWindowRoot);
//
//        Image scanIcon = new Image(getClass().getResourceAsStream("/com/themaestrocode/images/scan icon.png"));
//
//        getAddUserStage().initModality(Modality.APPLICATION_MODAL);
//        getAddUserStage().setScene(scannerWindowScene);
//        getAddUserStage().setTitle("Scanner Window");
//        getAddUserStage().getIcons().add(scanIcon);
//        getAddUserStage().setResizable(false);
//        getAddUserStage().show();
//    }

    private void setScanIconAnimation(boolean show) {
        RotateTransition rotate = new RotateTransition();

        if(show == true) {
            scanIcon.setVisible(true);

            rotate.setNode(scanIcon);
            rotate.setDuration(Duration.millis(3000));
            rotate.setCycleCount(TranslateTransition.INDEFINITE);
            rotate.setInterpolator(Interpolator.LINEAR);
            rotate.setByAngle(360);
            rotate.play();
        }
        else {
            rotate.stop();
            scanIcon.setVisible(false);
        }
    }

    public void changeButtonColor() {
        saveUserButton.setStyle("-fx-background-color: #113C14");
    }

    public void reverseButtonColor() {
        saveUserButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #006400, #32CD32)");
    }

    private boolean validateImageUpload(String image) {
        if(image == null) {
            System.out.println("image not validated");
            return false;
        }
        return true;
    }

    public void setLabelAttribute(Label name, String color, String text) {
        name.setStyle(color);
        name.setText(text);
    }
    public Stage getAddUserStage() {
        return addUserStage;
    }

    public void setAddUserStage(Stage addUserStage) {
        this.addUserStage = addUserStage;
    }

}
