package com.themaestrocode.aaualms;

import com.themaestrocode.aaualms.entity.Event;
import com.themaestrocode.aaualms.service.BookService;
import com.themaestrocode.aaualms.service.EventService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Label numberOfBooksIssuedLabel, numberOfBooksDueLabel, numberOfBooksAvailable;
    @FXML
    private TextField searchUserTextField, searchBookTextField;
    @FXML
    private TableColumn<Event, Integer> indexTableColumn;
    @FXML
    private TableColumn<Event, Integer> eventIdTableColumn;
    @FXML
    private TableColumn<Event, String> eventTypeTableColumn;
    @FXML
    private TableColumn<Event, String> bookIdTableColumn;
    @FXML
    private TableColumn<Event, String> userLibraryIdTableColumn;
    @FXML
    private TableColumn<Event, Timestamp> dateAndTimeTableColumn;
    @FXML
    private TableView<Event> todaysEventTableView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTodaysRecord();

        EventService eventService = new EventService();

        List<Event> todaysEvent = eventService.getTodaysEvents();

        indexTableColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(todaysEventTableView.getItems().indexOf(column.getValue()) + 1));
        eventIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("eventId"));
        eventTypeTableColumn.setCellValueFactory(new PropertyValueFactory<>("eventType"));
        bookIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        userLibraryIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("userLibraryId"));
        dateAndTimeTableColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));

        todaysEventTableView.getItems().addAll(todaysEvent);
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
