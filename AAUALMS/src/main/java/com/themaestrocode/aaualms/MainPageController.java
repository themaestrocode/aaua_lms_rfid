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

    /**
     * closes the current scene and opens the login page on the stage. The LoginPageController object can be used to access the method from anywhere.
     * @param event
     * @throws IOException
     */
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

    /**
     * sets the appropriate values for the bars on the dashboard to give a numerical report on the day's transactions.
     * It uses the BookService object to fetch the data from the database.
     */
    public void updateTodaysRecord() {
        BookService bookService = new BookService();

        numberOfBooksIssuedLabel.setText(bookService.booksIssuedToday());
        numberOfBooksDueLabel.setText(bookService.booksDueToday());
        numberOfBooksAvailable.setText(bookService.availableBooks());
    }

    /**
     * loads the main page scene unto the primary stage.
     * Can be called by any class from where access to the main page is needed.
     * @param event
     * @throws IOException
     */
    public void loadMainPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainPageScene.fxml"));
        ((Node) event.getSource()).getScene().setRoot(root);
    }

    public void changeButtonColor() {
        logoutButton.setStyle("-fx-background-color: #6F1515");
    }

    public void reverseButtonColor() {
        logoutButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #AA0000, #EF0107)");
    }

    /**
     * opens ContextMenu items to select an action option
     * @param event
     */
    public void manageUsersOptions(ActionEvent event) {
        ContextMenu contextMenu = new ContextMenu();

        //populating the ContextMenu node with the menu items
        MenuItem addUserItem = new MenuItem("Add Student/Staff");
        MenuItem seeAllStudentsItem = new MenuItem("See all Students");
        MenuItem seeAllStaffItem = new MenuItem("See all Staff");

        addUserItem.setOnAction(e -> {
            // Handle "Add Student" action
            ManageUsersMenuController manageUsersMenuController = new ManageUsersMenuController();

            try {
                manageUsersMenuController.loadAddUserPage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("Adding a student/staff...");
        });

        seeAllStudentsItem.setOnAction(e -> {
            System.out.println("showing all students...");
        });

        seeAllStaffItem.setOnAction(e -> {
            System.out.println("showing all staff...");
        });

        contextMenu.getItems().addAll(addUserItem, seeAllStudentsItem, seeAllStaffItem);

        // Get the scene coordinates of the Hyperlink
        double x = manageUsersLink.localToScreen(manageUsersLink.getBoundsInLocal()).getMinX();
        double y = manageUsersLink.localToScreen(manageUsersLink.getBoundsInLocal()).getMaxY();

        // Show the context menu near the Hyperlink
        contextMenu.show(manageUsersLink, x, y);
    }
}
