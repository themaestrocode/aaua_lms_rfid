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

public class AddUserPageController implements Initializable {

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
    private String userLibraryId;
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
        //populate the level combo box
        levelComboBox.getItems().addAll("100L", "200L", "300L", "400L", "500L", "PGD");
        departmentComboBox.setEditable(false);
        //hides the userLibraryTextField to achieve the RFID card scanning seamless functionality
        userLibraryIdTextField.setOpacity(0);
        //sets the scanIcon visibility to false until the scanCard method is called
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
     * called when a faculty gets selected from the faculty Combo box items list.
     * It also populates the department combo box based on the selected faculty
     */
    public void chooseFaculty() {
        DepartmentService departmentService = new DepartmentService();

        String selectedFaculty = (String) facultyComboBox.getValue();
        facultyTextField.setText(selectedFaculty);

        departmentComboBox.setItems(departmentService.getFacultiesUnderSelectedDepartment(selectedFaculty));
    }

    /**
     * called when a department gets selected from the department Combo box items list.
     */
    public void chooseDepartment() {
        String selectedDepartment = (String) departmentComboBox.getValue();
        departmentTextField.setText(selectedDepartment);
    }

    /**
     * called when a level gets selected from the level Combo box items list.
     */
    public void chooseLevel() {
        String selectedLevel = (String) levelComboBox.getValue();
        levelTextField.setText(selectedLevel);
    }

    /**
     * called by the uploadPhotoButton: to choose the photo of the user from the system
     * @return selectedFile or null
     */
    public File uploadPhoto() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Upload User Photo");

        //setting the initial directory where the file chooser opens by default
        File initialDirectory = new File("C:\\Users\\VICTOR A. SODERU\\Documents\\USERS PHOTO ALBUM");
        fileChooser.setInitialDirectory(initialDirectory);

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG files", "*.jpg")
        );

        File selectedFile = fileChooser.showOpenDialog(this.getAddUserStage());

        if(selectedFile != null) {
            imagePath = selectedFile.getAbsolutePath();
            System.out.println(imagePath);
            setLabelAttribute(photoUploadConfirmationLabel, "-fx-text-fill: #006400", "Photo uploaded!");
            setLabelAttribute(scanCardConfirmationLabel, "-fx-text-fill: black", "waiting for user to scan card...");
            return selectedFile;
        }
        return null;
    }

    public void scanCard() {
        UserService userService = new UserService();

        if(!userService.validateImageUpload(imagePath)) {
            setLabelAttribute(photoUploadConfirmationLabel, "-fx-text-fill: #AA0000", "Photo not yet uploaded!");
        }

        userLibraryIdTextField.setDisable(false);

        setScanIconAnimation(true);

        userLibraryIdTextField.requestFocus();
        userLibraryIdTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.length() == 10) {
                boolean userFound = false;

                userFound = userService.findUser(newValue);

                if (userFound) {
                    Platform.runLater(() -> {
                        userLibraryIdTextField.clear();
                        System.out.println("id already registered!");
                        scanCardConfirmationLabel.setText("");
                        showAlert("Notification: Card", "This card has already been registered with another user!");
                    });
                }
                else {
                    setScanIconAnimation(false);
                    userLibraryId = newValue;
                    userLibraryIdTextField.setDisable(true);
                    //userLibraryIdTextField.clear();
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
            User student = new User(userLibraryId, userIdTextField.getText(), firstNameTextField.getText(), lastNameTextField.getText(), imagePath, facultyTextField.getText(),
                    departmentTextField.getText(), levelTextField.getText(), phoneNoTextField.getText(), emailTextField.getText());
            userSaved = userService.saveStudent(student);
        }
        else if(staffRadioButton.isSelected()) {
            User staff = new User(userLibraryId, userIdTextField.getText(), firstNameTextField.getText(), lastNameTextField.getText(), imagePath, facultyTextField.getText(),
                    departmentTextField.getText(), phoneNoTextField.getText(), emailTextField.getText());
            userSaved = userService.saveStaff(staff);
        }

        if(userSaved) {
//            if(!saveUserOutcomeLabel.getText().isEmpty()) {
//                saveUserOutcomeLabel.setText("");
//            }
            setLabelAttribute(saveUserOutcomeLabel, "fx-text-fill: #006400", "User saved!");
            showAlert("Notification: New User Added", "User successfully added as a patron!");
            //addUserStage.close();
        }
        else {
            if(!userService.validateUserId(userIdTextField.getText())) {
                setTextFieldInvalidAttribute(userIdTextField);
            }
            else {
                if(userIdTextField.getText().equals("invalid or empty!")) {
                    reverseTextFieldColor(userIdTextField);
                }
            }

            if(!userService.validateFirstName(firstNameTextField.getText())) {
                setTextFieldInvalidAttribute(firstNameTextField);
            }
            else {
                if(firstNameTextField.getText().equals("invalid or empty!")) {
                    reverseTextFieldColor(firstNameTextField);
                }
            }

            if(!userService.validateLastName(lastNameTextField.getText())) {
                setTextFieldInvalidAttribute(lastNameTextField);
            }
            else {
                if(lastNameTextField.getText().equals("invalid or empty!")) {
                    reverseTextFieldColor(lastNameTextField);
                }
            }

            if(!userService.validateFaculty(facultyTextField.getText())) {
                setTextFieldInvalidAttribute(facultyTextField);
            }
            else {
                if(facultyTextField.getText().equals("invalid or empty!")) {
                    reverseTextFieldColor(facultyTextField);
                }
            }

            if(!userService.validateDepartment(departmentTextField.getText())) {
                setTextFieldInvalidAttribute(departmentTextField);
            }
            else {
                if(departmentTextField.getText().equals("invalid or empty!")) {
                    reverseTextFieldColor(departmentTextField);
                }
            }

            if(!userService.validateLevel(levelTextField.getText())) {
                setTextFieldInvalidAttribute(levelTextField);
            }
            else {
                if(levelTextField.getText().equals("invalid or empty!")) {
                    reverseTextFieldColor(levelTextField);
                }
            }

            if(!userService.validatePhoneNumber(phoneNoTextField.getText())) {
                setTextFieldInvalidAttribute(phoneNoTextField);
            }
            else {
                if(phoneNoTextField.getText().equals("invalid or empty!")) {
                    reverseTextFieldColor(phoneNoTextField);
                }
            }

            if(!userService.validateEmail(emailTextField.getText())) {
                setTextFieldInvalidAttribute(emailTextField);
            }
            else {
                if(emailTextField.getText().equals("invalid or empty!")) {
                    reverseTextFieldColor(emailTextField);
                }
            }

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

    public void setTextFieldInvalidAttribute(TextField name) {
        name.setStyle("-fx-text-fill: #AA0000");
        name.setText("invalid or empty!");
    }

    public void reverseTextFieldColor(TextField name) {
        name.setStyle("-fx-text-fill: black");
    }

    public void changeLoginButtonColor() {
        saveUserButton.setStyle("-fx-background-color: #113C14");
    }

    public void reverseLoginButtonColor() {
        saveUserButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #006400, #32CD32)");
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
