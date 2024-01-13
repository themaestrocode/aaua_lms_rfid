package com.themaestrocode.aaualms;

import javafx.event.ActionEvent;
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

public class MainPageController implements Initializable {

    @FXML
    private Button logoutButton;
    @FXML
    private Hyperlink manageUsersLink, manageBooksLink;

    private LoginPageController loginPageController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * called by the home button of the MainPageController and opens the dashboard scene
     * @param event
     * @throws IOException
     */
    public void goToDashboard(ActionEvent event) throws IOException {
        DashboardController dashboardController = new DashboardController();

        dashboardController.loadDashboard(event);
    }

    /**
     * closes the current page and opens the login page on the stage.
     * @param event
     */
    public void logout(ActionEvent event) {
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
     * opens ContextMenu items to select an action option
     * @param event
     */
    public void manageUsersOptions(ActionEvent event) {
        ContextMenu contextMenu = new ContextMenu();

        //populating the ContextMenu node with the menu items
        MenuItem addUserItem = new MenuItem("Add a new Student/Staff");
        MenuItem seeAllStudentsItem = new MenuItem("See all Students");
        MenuItem seeAllStaffItem = new MenuItem("See all Staff");

        addUserItem.setOnAction(e -> {
            try {
                loadAddUserPage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("Adding a student/staff...");
        });

        seeAllStudentsItem.setOnAction(e -> {
            AllStudentsPageController allStudentsPageController = new AllStudentsPageController();

            try {
                allStudentsPageController.loadAllStudentsPage(event);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("showing all students...");
        });

        seeAllStaffItem.setOnAction(e -> {
            AllStaffPageController allStaffPageController = new AllStaffPageController();

            try {
                allStaffPageController.loadAllStaffPage(event);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("showing all staff...");
        });

        contextMenu.getItems().addAll(addUserItem, seeAllStudentsItem, seeAllStaffItem);

        // Get the scene coordinates of the Hyperlink
        double x = manageUsersLink.localToScreen(manageUsersLink.getBoundsInLocal()).getMinX();
        double y = manageUsersLink.localToScreen(manageUsersLink.getBoundsInLocal()).getMaxY();

        // Show the context menu near the Hyperlink
        contextMenu.show(manageUsersLink, x, y);
    }

    public void manageBooksOptions(ActionEvent event) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem addBookItem = new MenuItem("Add a new Book");
        MenuItem seeAllBooksItem = new MenuItem("See all Books");
        MenuItem seeAllBorrowedBooksItems = new MenuItem("See all borrowed Books");

        addBookItem.setOnAction(e -> {
            try {
                loadAddBookPage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("adding a new book...");
        });

        seeAllBooksItem.setOnAction(e -> {
            AllBooksPageController allBooksPageController = new AllBooksPageController();

            try {
                allBooksPageController.loadAllBooksPage(event);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("showing all books...");
        });

        seeAllBorrowedBooksItems.setOnAction(e -> {
            AllBorrowedBooksPageController allBorrowedBooksPageController = new AllBorrowedBooksPageController();

            try {
                allBorrowedBooksPageController.loadAllBorrowedBooksPage(event);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("showing all borrowed books...");
        });

        contextMenu.getItems().addAll(addBookItem, seeAllBooksItem, seeAllBorrowedBooksItems);

        double x = manageBooksLink.localToScreen(manageUsersLink.getBoundsInLocal()).getMinX();
        double y = manageBooksLink.localToScreen(manageUsersLink.getBoundsInLocal()).getMaxY();

        contextMenu.show(manageBooksLink, x, y);
    }

    /**
     * loads/opens a non-resizable stage: "the add user page" upon the main page for entering student/staff details to be added.
     * @throws IOException
     */
    public void loadAddUserPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addUserPage.fxml"));
        Parent root = fxmlLoader.load();

        AddUserPageController addUserPageController = fxmlLoader.getController();
        Stage addUserStage = new Stage();
        addUserPageController.setAddUserStage(addUserStage);

        Scene scene = new Scene(root);

        String css = this.getClass().getResource("/com/themaestrocode/css/styling.css").toExternalForm();
        scene.getStylesheets().add(css);

        Image userIcon = new Image(getClass().getResourceAsStream("/com/themaestrocode/images/a user icon.png"));

        //Stage addUserStage = new Stage();
        addUserStage.initModality(Modality.APPLICATION_MODAL);
        addUserStage.setTitle("Add User");
        addUserStage.setScene(scene);
        addUserStage.getIcons().add(userIcon);
        addUserStage.setResizable(false);
        addUserStage.show();
    }

    /**
     * loads/opens a non-resizable stage: "the add book page" upon the main page for entering book details to be added.
     * @throws IOException
     */
    public void loadAddBookPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addBookPage.fxml"));
        Parent root = fxmlLoader.load();

        AddBookPageController addBookPageController = fxmlLoader.getController();
        Stage addBookStage = new Stage();
        addBookPageController.setAddBookStage(addBookStage);

        Scene scene = new Scene(root);

        String css = this.getClass().getResource("/com/themaestrocode/css/styling.css").toExternalForm();
        scene.getStylesheets().add(css);

        Image bookIcon = new Image(getClass().getResourceAsStream("/com/themaestrocode/images/book icon.png"));

        //Stage addUserStage = new Stage();
        addBookStage.initModality(Modality.APPLICATION_MODAL);
        addBookStage.setTitle("Add Book");
        addBookStage.setScene(scene);
        addBookStage.getIcons().add(bookIcon);
        addBookStage.setResizable(false);
        addBookStage.show();
    }

    public void changeButtonColor() {
        logoutButton.setStyle("-fx-background-color: #6F1515");
    }

    public void reverseButtonColor() {
        logoutButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #AA0000, #EF0107)");
    }
}
