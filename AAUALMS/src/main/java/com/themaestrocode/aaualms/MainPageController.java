package com.themaestrocode.aaualms;

import com.themaestrocode.aaualms.service.BookService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {

    @FXML
    private Button logoutButton;
    @FXML
    private Label numberOfBooksIssuedLabel, numberOfBooksDueLabel, numberOfBooksAvailable;
    @FXML
    private Hyperlink manageUsersLink;

    private Parent root;
    private Stage stage;
    private Scene scene;

    private LoginPageController loginPageController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTodaysRecord();
    }

    public void logout(ActionEvent event) throws IOException {
        Alert logoutAlert = new Alert(Alert.AlertType.CONFIRMATION);
        logoutAlert.setTitle("Notification");
        logoutAlert.setHeaderText("Logout");
        logoutAlert.setContentText("Are you sure you want to logout?");

        if(logoutAlert.showAndWait().get() == ButtonType.OK) {
            try {
                loginPageController = new LoginPageController();
                loginPageController.loadLoginPage(event);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateTodaysRecord() {
        BookService bookService = new BookService();

        numberOfBooksIssuedLabel.setText(bookService.booksIssuedToday());
        numberOfBooksDueLabel.setText(bookService.booksDueToday());
        numberOfBooksAvailable.setText(bookService.availableBooks());
    }

    public void loadMainPage(ActionEvent event) throws IOException, SQLException {
        Parent root = FXMLLoader.load(getClass().getResource("mainPageScene.fxml"));
        ((Node) event.getSource()).getScene().setRoot(root);
    }

    public void changeButtonColor() {
        logoutButton.setStyle("-fx-background-color: #6F1515");
    }

    public void reverseButtonColor() {
        logoutButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #AA0000, #EF0107)");
    }

    public void manageUsersOptions(ActionEvent event) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem addStudentItem = new MenuItem("Add Student");
        MenuItem addStaffItem = new MenuItem("Add Staff");
        MenuItem seeAllStudentsItem = new MenuItem("See all Students");
        MenuItem seeAllStaffItem = new MenuItem("See all Staff");

        addStudentItem.setOnAction(e -> {
            // Handle "Add Student" action
            System.out.println("Adding a student...");
        });

        addStaffItem.setOnAction(e -> {
            // Handle "Add Staff" action
            System.out.println("Adding a staff member...");
        });

        seeAllStudentsItem.setOnAction(e -> {
            System.out.println("showing all students...");
        });

        seeAllStaffItem.setOnAction(e -> {
            System.out.println("showing all staff...");
        });

        contextMenu.getItems().addAll(addStudentItem, addStaffItem,seeAllStudentsItem, seeAllStaffItem);

        // Get the scene coordinates of the Hyperlink
        double x = manageUsersLink.localToScreen(manageUsersLink.getBoundsInLocal()).getMinX();
        double y = manageUsersLink.localToScreen(manageUsersLink.getBoundsInLocal()).getMaxY();

        // Show the context menu near the Hyperlink
        contextMenu.show(manageUsersLink, x, y);
    }
}
