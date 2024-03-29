package com.themaestrocode.aaualms;

import com.themaestrocode.aaualms.utility.UtilityMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    private UtilityMethods utilityMethods = new UtilityMethods();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        logoutButton.setOnMouseEntered(mouseEvent -> {
            utilityMethods.changeRedButtonColor(logoutButton);
        });

        logoutButton.setOnMouseExited(mouseEvent -> {
            utilityMethods.reverseRedButtonColor(logoutButton);
        });
    }

    /**
     * called by the home button of the MainPageController and opens the dashboard scene
     * @param event
     * @throws IOException
     */
    public void goToDashboard(ActionEvent event) {
        DashboardController dashboardController = new DashboardController();

        try {
            dashboardController.loadDashboard(event);
        }
        catch (Exception e) {
            utilityMethods.showInformationAlert("Error", "Failed to load!", "The requested page has failed to load. Please try again later.");
            e.printStackTrace();
        }
    }

    public void goToInsightPage(ActionEvent event) throws IOException {
        InsightPageController insightPageController = new InsightPageController();

        try {
            insightPageController.loadInsightPage(event);
        }
        catch (Exception e) {
            utilityMethods.showInformationAlert("Error", "Failed to load!", "The requested page has failed to load. Please try again later.");
        }
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
            LoginPageController loginPageController = new LoginPageController();

            try {
                loginPageController.loadLoginPage(event);
            }
            catch (Exception e) {
                utilityMethods.showInformationAlert("Error", "Failed to load!", "The requested page has failed to load. Please try again later.");
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
                utilityMethods.showErrorAlert("Failed to load page!", "The requested page could not be loaded.");
                //throw new RuntimeException(ex);
            }
        });

        seeAllStudentsItem.setOnAction(e -> {
            AllStudentsPageController allStudentsPageController = new AllStudentsPageController();

            try {
                allStudentsPageController.loadAllStudentsPage(event);
            } catch (IOException ex) {
                utilityMethods.showErrorAlert("Failed to load page!", "The requested page could not be loaded.");
                //throw new RuntimeException(ex);
            }
        });

        seeAllStaffItem.setOnAction(e -> {
            AllStaffPageController allStaffPageController = new AllStaffPageController();

            try {
                allStaffPageController.loadAllStaffPage(event);
            } catch (IOException ex) {
                utilityMethods.showErrorAlert("Failed to load page!", "The requested page could not be loaded.");
                //throw new RuntimeException(ex);
            }
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
            }
            catch (IOException ex) {
                utilityMethods.showErrorAlert("Failed to load page!", "The requested page could not be loaded.");
                //throw new RuntimeException(ex);
            }
        });

        seeAllBooksItem.setOnAction(e -> {
            AllBooksPageController allBooksPageController = new AllBooksPageController();

            try {
                allBooksPageController.loadAllBooksPage(event);
            }
            catch (IOException ex) {
                utilityMethods.showErrorAlert("Failed to load page!", "The requested page could not be loaded.");
                //throw new RuntimeException(ex);
            }
        });

        seeAllBorrowedBooksItems.setOnAction(e -> {
            AllBorrowedBooksPageController allBorrowedBooksPageController = new AllBorrowedBooksPageController();

            try {
                allBorrowedBooksPageController.loadAllBorrowedBooksPage(event);
            }
            catch (IOException ex) {
                utilityMethods.showErrorAlert("Failed to load page!", "The requested page could not be loaded.");
                //throw new RuntimeException(ex);
            }
        });

        contextMenu.getItems().addAll(addBookItem, seeAllBooksItem, seeAllBorrowedBooksItems);

        double x = manageBooksLink.localToScreen(manageUsersLink.getBoundsInLocal()).getMinX();
        double y = manageBooksLink.localToScreen(manageUsersLink.getBoundsInLocal()).getMaxY();

        contextMenu.show(manageBooksLink, x, y);
    }

    public void issueBook() {
        try {
            loadIssueBookPage();
        }
        catch (Exception e){
            utilityMethods.showErrorAlert("Failed to load page!", "The requested page could not be loaded.");
            //throw new RuntimeException(ex);
        }
    }

    public void returnBook() {
        try {
            loadReturnBookPage();
        }
        catch (IOException e) {
            utilityMethods.showErrorAlert("Failed to load page!", "The requested page could not be loaded.");
            //throw new RuntimeException(ex);
        }
    }

    public void loadSettingsPage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("settingsPage.fxml"));
            ((Node) event.getSource()).getScene().setRoot(root);
        }
        catch (Exception e) {

        }
    }

    /**
     * loads/opens a non-resizable stage: "the add user page" upon the main page for entering student/staff details to be added.
     * @throws IOException
     */
    private void loadAddUserPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addUserPage.fxml"));
        Parent root = fxmlLoader.load();

        AddUserPageController addUserPageController = fxmlLoader.getController();
        Stage addUserStage = new Stage();
        addUserPageController.setAddUserStage(addUserStage);

        Scene scene = new Scene(root);

        String css = this.getClass().getResource("/com/themaestrocode/css/styling.css").toExternalForm();
        scene.getStylesheets().add(css);

        Image userIcon = new Image(getClass().getResourceAsStream("/com/themaestrocode/images/a user icon.png"));

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
    private void loadAddBookPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addBookPage.fxml"));
        Parent root = fxmlLoader.load();

        AddBookPageController addBookPageController = fxmlLoader.getController();
        Stage addBookStage = new Stage();
        addBookPageController.setAddBookStage(addBookStage);

        Scene scene = new Scene(root);

        String css = this.getClass().getResource("/com/themaestrocode/css/styling.css").toExternalForm();
        scene.getStylesheets().add(css);

        Image bookIcon = new Image(getClass().getResourceAsStream("/com/themaestrocode/images/book icon.png"));

        addBookStage.initModality(Modality.APPLICATION_MODAL);
        addBookStage.setTitle("Add Book");
        addBookStage.setScene(scene);
        addBookStage.getIcons().add(bookIcon);
        addBookStage.setResizable(false);
        addBookStage.show();
    }

    private void loadIssueBookPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("issueBookPage.fxml"));
        Parent root = fxmlLoader.load();

        IssueBookPageController issueBookPageController = fxmlLoader.getController();
        Stage issueBookStage = new Stage();
        issueBookPageController.setIssueBookStage(issueBookStage);

        Scene scene = new Scene(root);

        String css = this.getClass().getResource("/com/themaestrocode/css/styling.css").toExternalForm();
        scene.getStylesheets().add(css);

        Image issueBookIcon = new Image(getClass().getResourceAsStream("/com/themaestrocode/images/book icon.png"));

        issueBookStage.initModality(Modality.APPLICATION_MODAL);
        issueBookStage.setTitle("Issue Book");
        issueBookStage.setScene(scene);
        issueBookStage.getIcons().add(issueBookIcon);
        issueBookStage.setResizable(false);
        issueBookStage.show();
    }

    private void loadReturnBookPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("returnBookPage.fxml"));
        Parent root = fxmlLoader.load();

        ReturnBookPageController returnBookPageController = fxmlLoader.getController();
        Stage returnBookStage = new Stage();
        returnBookPageController.setReturnBookStage(returnBookStage);

        Scene scene = new Scene(root);

        String css = this.getClass().getResource("/com/themaestrocode/css/styling.css").toExternalForm();
        scene.getStylesheets().add(css);

        Image bookIcon = new Image(getClass().getResourceAsStream("/com/themaestrocode/images/book icon.png"));

        returnBookStage.initModality(Modality.APPLICATION_MODAL);
        returnBookStage.setTitle("Return Book");
        returnBookStage.setScene(scene);
        returnBookStage.getIcons().add(bookIcon);
        returnBookStage.setResizable(false);
        returnBookStage.show();
    }
}
