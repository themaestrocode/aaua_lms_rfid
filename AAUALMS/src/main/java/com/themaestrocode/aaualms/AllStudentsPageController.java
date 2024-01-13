package com.themaestrocode.aaualms;

import com.themaestrocode.aaualms.entity.User;
import com.themaestrocode.aaualms.service.UserService;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AllStudentsPageController implements Initializable {

    @FXML
    private TableColumn<User, Integer> indexTableColumn;
    @FXML
    private TableColumn<User, String> libraryIdTableColumn;
    @FXML
    private TableColumn<User, String> firstNameTableColumn;
    @FXML
    private TableColumn<User, String> lastNameTableColumn;
    @FXML
    private TableColumn<User, String> matricNoTableColumn;
    @FXML
    private TableColumn<User, String> levelTableColumn;
    @FXML
    private TableView<User> studentsTableView;
    @FXML
    private Label totalStudentLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserService userService = new UserService();

        List<User> students = userService.getAllStudents();

        indexTableColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(studentsTableView.getItems().indexOf(column.getValue()) + 1));
        libraryIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("userLibraryId"));
        firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        matricNoTableColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        levelTableColumn.setCellValueFactory(new PropertyValueFactory<>("level"));

        studentsTableView.getItems().addAll(students);

        totalStudentLabel.setText(String.valueOf(studentsTableView.getItems().size()));

        studentsTableView.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                User selectedUser = studentsTableView.getSelectionModel().getSelectedItem();

                if (selectedUser != null) {
                    showDetailedView(selectedUser); // Show detailed view for the selected user
                }
            }
        });
    }

    public void loadAllStudentsPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("allStudentsPage.fxml"));
        ((Node) event.getSource()).getScene().setRoot(root);
    }

    public void showDetailedView(User selectedUser) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("entityDetailsView.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);

            String css = this.getClass().getResource("/com/themaestrocode/css/styling.css").toExternalForm();
            scene.getStylesheets().add(css);

            Image image = new Image(getClass().getResourceAsStream("/com/themaestrocode/images/a user icon.png"));

            // Get the controller associated with the detailed view
            EntityDetailsController entityDetailsController = fxmlLoader.getController();

            // Pass the selected user to the detailed view controller
            entityDetailsController.studentData(selectedUser);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Student Details");
            stage.getIcons().add(image);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
