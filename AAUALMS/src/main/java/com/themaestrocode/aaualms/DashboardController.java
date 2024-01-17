package com.themaestrocode.aaualms;

import com.themaestrocode.aaualms.entity.Book;
import com.themaestrocode.aaualms.entity.Event;
import com.themaestrocode.aaualms.entity.User;
import com.themaestrocode.aaualms.service.BookService;
import com.themaestrocode.aaualms.service.EventService;
import com.themaestrocode.aaualms.service.UserService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

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
    private VBox userListViewBox, bookListViewBox;
    @FXML
    private ListView searchUserListView, searchBookListView;
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
    @FXML
    private ImageView searchIcon;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userListViewBox.setVisible(false); //makes the search user list view invisible when the page loads up
        bookListViewBox.setVisible(false);

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

//        searchUserListView.setOnMouseClicked(mouseEvent -> {
//            if(mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
//                String username = (String) searchUserListView.getSelectionModel().getSelectedItem();
//
//                UserService userService = new UserService();
//                User user = userService.findUserByName(username);
//
//                if(user != null) {
//                    String userLibraryId = userService.fi
//                }
//
//            }
//        });
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

    public void performSearch() {
        if(searchUserTextField.isFocused()) {searchUserDisplay();}
        else if(searchBookTextField.isFocused()) {searchBookDisplay();}
    }

    private void searchUserDisplay() {
        UserService userService = new UserService();

        List<User> users = userService.getAllUsers(); //fetch users from the DB
        ObservableList<User> observableUsers = FXCollections.observableArrayList(users); //convert the user list to observable list to be used in the search implementation

        // captures the text in the text field with the searchItem variable and converts it to lowercase
        String searchItem = searchUserTextField.getText().toLowerCase();

        //create a new observable list that will contain user results matching the value of the searchItem string variable
        ObservableList<String> searchResults = FXCollections.observableArrayList();

        for(User user: observableUsers) {
            String fullName = user.getFirstName().toLowerCase() + " " + user.getLastName().toLowerCase();
            String matricOrStaffId = user.getUserId();

            //this allows the search to be carried out using either userId (i.e. matric no for student or staff id for staff) or name
            if(matricOrStaffId.contains(searchItem)) {searchResults.add(fullName);}
        }

        searchUserListView.setItems(searchResults);

        //this ensures that the list view is only visible when a search is being carried out.
        userListViewBox.setVisible(!searchItem.isEmpty());
    }

    private void searchBookDisplay() {
        BookService bookService = new BookService();

        List<Book> books = bookService.getAllBooks();
        ObservableList<Book> observableBooks = FXCollections.observableArrayList(books);

        String searchItem = searchBookTextField.getText().toLowerCase();

        ObservableList<String> searchResults = FXCollections.observableArrayList();

        for(Book book: observableBooks) {
            String title = book.getTitle().toLowerCase();
            String author = book.getAuthor().toLowerCase();

            if(title.contains(searchItem) || author.contains(searchItem)) {searchResults.add(title);}

            searchBookListView.setItems(searchResults);

            bookListViewBox.setVisible(!searchItem.isEmpty());
        }
    }

    public void closeUserListView() {
        userListViewBox.setVisible(false);
    }

    public void closeBookListView() {
        bookListViewBox.setVisible(false);
    }
}
