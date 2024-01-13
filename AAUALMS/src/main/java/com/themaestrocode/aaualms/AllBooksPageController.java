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

public class AllBooksPageController implements Initializable {

    @FXML
    private TableColumn<Book, Integer> indexTableColumn;
    @FXML
    private TableColumn<Book, String> bookIdTableColumn;
    @FXML
    private TableColumn<Book, String> titleTableColumn;
    @FXML
    private TableColumn<Book, String> authorTableColumn;
    @FXML
    private TableColumn<Book, String> shelveNoTableColumn;
    @FXML
    private TableColumn<Book, String> bookStatusTableColumn;
    @FXML
    private TableView<Book> booksTableView;
    @FXML
    private Label totalBooksLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BookService bookService = new BookService();

        List<Book> books = bookService.getAllBooks();

        indexTableColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(booksTableView.getItems().indexOf(column.getValue()) + 1));
        bookIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorTableColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        shelveNoTableColumn.setCellValueFactory(new PropertyValueFactory<>("shelveNo"));
        bookStatusTableColumn.setCellValueFactory(new PropertyValueFactory<>("bookStatus"));

        booksTableView.getItems().addAll(books);

        totalBooksLabel.setText(String.valueOf(booksTableView.getItems().size()));

        booksTableView.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                Book selectedBook = booksTableView.getSelectionModel().getSelectedItem();

                if (selectedBook != null) {
                    showDetailedView(selectedBook); // Show detailed view for the selected user
                }
            }
        });
    }

    public void loadAllBooksPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("allBooksPage.fxml"));
        ((Node) event.getSource()).getScene().setRoot(root);
    }

    private void showDetailedView(Book selectedBook) {
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
            entityDetailsController.bookData(selectedBook);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Book Details");
            stage.getIcons().add(image);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
