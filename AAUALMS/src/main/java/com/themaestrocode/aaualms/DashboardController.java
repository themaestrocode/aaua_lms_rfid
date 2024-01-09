package com.themaestrocode.aaualms;

import com.themaestrocode.aaualms.service.BookService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Label numberOfBooksIssuedLabel, numberOfBooksDueLabel, numberOfBooksAvailable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTodaysRecord();
    }

    /**
     * loads the main page scene unto the primary stage.
     * Can be called by any class from where access to the main page is needed.
     * @param event
     * @throws IOException
     */
    public void loadDashboard(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        ((Node) event.getSource()).getScene().setRoot(root);
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

}
