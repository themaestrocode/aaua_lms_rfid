package com.themaestrocode.aaualms;

import com.themaestrocode.aaualms.entity.User;
import com.themaestrocode.aaualms.service.DepartmentService;
import com.themaestrocode.aaualms.service.FacultyService;
import com.themaestrocode.aaualms.service.UserService;
import com.themaestrocode.aaualms.utility.UtilityMethods;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class AddUserPageController implements Initializable {

    @FXML
    private Button addUserButton;
    @FXML
    private Label userIdLabel, photoUploadConfirmationLabel, scanCardConfirmationLabel, addUserOutcomeLabel;
    @FXML
    private TextField userLibraryIdTextField, userIdTextField, firstNameTextField, lastNameTextField, facultyTextField, departmentTextField, levelTextField, phoneNoTextField, emailTextField;
    @FXML
    private ComboBox facultyComboBox, departmentComboBox, levelComboBox;
    @FXML
    private RadioButton studentRadioButton, staffRadioButton;
    @FXML
    private ImageView scanIcon;

    private Stage addUserStage;
    private String userLibraryId, imagePath;

    private UtilityMethods utilityMethods = new UtilityMethods();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addUserButton.setOnMouseEntered(mouseEvent -> {
            utilityMethods.changeGreenButtonColor(addUserButton);
        });

        addUserButton.setOnMouseExited(mouseEvent -> {
            utilityMethods.reverseGreenButtonColor(addUserButton);
        });

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

        departmentComboBox.setItems(departmentService.getDepartmentsUnderSelectedFaculty(selectedFaculty));
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
     * called by the uploadPhotoButton: to choose the photo of the user from the system.
     * Its file selection operation is performed by the uploadEntityImageFile method of the UtilityMethods class
     */
    public void uploadPhoto() {
        File userImage = utilityMethods.uploadEntityImageFile("user", addUserStage);

        if(userImage != null) {
            imagePath = userImage.getAbsolutePath();
            System.out.println(imagePath);
            utilityMethods.setLabelAttribute(photoUploadConfirmationLabel, "-fx-text-fill: #006400", "Photo uploaded!");
            utilityMethods.setLabelAttribute(scanCardConfirmationLabel, "-fx-text-fill: black", "waiting for user to scan card...");
        }
        else {
            utilityMethods.setLabelAttribute(photoUploadConfirmationLabel, "-fx-text-fill: #AA0000", "Photo not uploaded!");
        }
    }

    public void scanCard() {
        UserService userService = new UserService();

        if(!userService.validateImageUpload(imagePath)) {
            utilityMethods.setLabelAttribute(photoUploadConfirmationLabel, "-fx-text-fill: #AA0000", "Photo not yet uploaded!");
        }

        userLibraryIdTextField.setDisable(false);

        utilityMethods.setScanIconAnimation(scanIcon, true);

        userLibraryIdTextField.requestFocus();
        userLibraryIdTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.length() == 10) {
                User user = userService.findUserByLibraryId(newValue);

                if (user != null) {
                    Platform.runLater(() -> {
                        userLibraryIdTextField.clear();
                        System.out.println("id already registered!");
                        scanCardConfirmationLabel.setText("");
                        utilityMethods.showInformationAlert("Error", "User card registration failed", "This card has already been registered with another user!");
                    });
                }
                else {
                    utilityMethods.setScanIconAnimation(scanIcon, false);
                    userLibraryId = newValue;
                    userLibraryIdTextField.setDisable(true);
                    //userLibraryIdTextField.clear();
                    scanCardConfirmationLabel.requestFocus();
                    System.out.println("id not yet registered: " + newValue);
                    utilityMethods.setLabelAttribute(scanCardConfirmationLabel, "-fx-text-fill: #006400", "card successfully scanned!");
                }
            }
        });
    }

    private final ChangeListener<String> scanCardTextFieldChangeListener = (observable, oldValue, newValue) -> {

    };

    public void addUser() {
        try {
            UserService userService = new UserService();

            boolean userSaved = false;

            if(studentRadioButton.isSelected()) {
                boolean isValid = userService.validateStudentDetails(userLibraryId, userIdTextField, firstNameTextField, lastNameTextField, imagePath, facultyTextField, departmentTextField,
                        levelTextField, phoneNoTextField, emailTextField);

                if(isValid) {
                    User student = createStudentObject();
                    userSaved = userService.addStudent(student);
                }
            }
            else if(staffRadioButton.isSelected()) {
                boolean isValid = userService.validateStaffDetails(userLibraryId, userIdTextField, firstNameTextField, lastNameTextField, imagePath, facultyTextField,
                        departmentTextField, phoneNoTextField, emailTextField);

                if(isValid) {
                    User staff = createStaffObject();
                    userSaved = userService.addStaff(staff);
                }
            }
            else {
                //an impossible scenario, but if no radio button is selected, this else statement executes. Just for error handling.
                utilityMethods.showInformationAlert("Error", "User not saved", "Please specify user type!");
                utilityMethods.setLabelAttribute(addUserOutcomeLabel, "-fx-text-fill: #AA0000", "User could not be saved!");
            }

            if(userSaved) {
                utilityMethods.showInformationAlert("Notification", "New User Added", "User successfully added as a patron!");
                //attempt to close the stage after successfully saving the user
                utilityMethods.closeStage(addUserStage);
            }
            else {
                utilityMethods.setLabelAttribute(addUserOutcomeLabel, "-fx-text-fill: #AA0000", "User could not be saved!");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * creates a new instance of the User class for a student user to be saved
     * @return user of type student
     */
    private User createStudentObject() {
        return new User(userLibraryId, userIdTextField.getText(), firstNameTextField.getText(), lastNameTextField.getText(), imagePath,
                        facultyTextField.getText(), departmentTextField.getText(), levelTextField.getText(), phoneNoTextField.getText(),
                        emailTextField.getText());
    }

    /**
     * creates a new instance of the User class for a staff user to be saved
     * @return user of type staff
     */
    private User createStaffObject() {
        return new User(userLibraryId, userIdTextField.getText(), firstNameTextField.getText(), lastNameTextField.getText(), imagePath,
                        facultyTextField.getText(), departmentTextField.getText(), phoneNoTextField.getText(), emailTextField.getText());
    }

    public Stage getAddUserStage() {
        return addUserStage;
    }

    public void setAddUserStage(Stage addUserStage) {
        this.addUserStage = addUserStage;
    }
}
