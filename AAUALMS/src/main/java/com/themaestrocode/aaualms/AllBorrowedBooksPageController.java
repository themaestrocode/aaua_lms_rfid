package com.themaestrocode.aaualms;

import com.themaestrocode.aaualms.entity.Book;
import com.themaestrocode.aaualms.service.BookService;
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

public class AllBorrowedBooksPageController implements Initializable {

    @FXML
    private TableColumn<Book.IssuedBook, Integer> indexTableColumn;
    @FXML
    private TableColumn<Book.IssuedBook, Integer> issueIdTableColumn;
    @FXML
    private TableColumn<Book.IssuedBook, String> titleTableColumn;
    @FXML
    private TableColumn<Book.IssuedBook, String> borrowerTableColumn;
    @FXML
    private TableColumn<Book.IssuedBook, String> borrowerTypeTableColumn;
    @FXML
    private TableColumn<Book.IssuedBook, String> issueDateTableColumn;
    @FXML
    private TableView<Book.IssuedBook> issuedBooksTableView;
    @FXML
    private Label totalBooksIssuedOutLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BookService bookService = new BookService();

        List<Book.IssuedBook> borrowedBooks = bookService.getAllBorrowedBooks();

        indexTableColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(issuedBooksTableView.getItems().indexOf(column.getValue()) + 1));
        issueIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("issueId"));
        titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        borrowerTableColumn.setCellValueFactory(new PropertyValueFactory<>("borrowerName"));
        borrowerTypeTableColumn.setCellValueFactory(new PropertyValueFactory<>("userType"));
        issueDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("issueDate"));

        issuedBooksTableView.getItems().addAll(borrowedBooks);

        totalBooksIssuedOutLabel.setText(String.valueOf(issuedBooksTableView.getItems().size()));

        issuedBooksTableView.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                Book.IssuedBook selectedBook = issuedBooksTableView.getSelectionModel().getSelectedItem();

                if (selectedBook != null) {
                    showDetailedView(selectedBook); // Show detailed view for the selected user
                }
            }
        });
    }

    public void loadAllBorrowedBooksPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("allBorrowedBooksPage.fxml"));
        ((Node) event.getSource()).getScene().setRoot(root);
    }

    private void showDetailedView(Book.IssuedBook selectedBook) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("entityDetailsView.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);

            String css = this.getClass().getResource("/com/themaestrocode/css/styling.css").toExternalForm();
            scene.getStylesheets().add(css);

            Image image = new Image(getClass().getResourceAsStream("/com/themaestrocode/images/book icon.png"));

            // Get the controller associated with the detailed view
            EntityDetailsController entityDetailsController = fxmlLoader.getController();

            // Pass the selected user to the detailed view controller
            entityDetailsController.issuedBookData(selectedBook);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Book Issue Details");
            stage.getIcons().add(image);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
