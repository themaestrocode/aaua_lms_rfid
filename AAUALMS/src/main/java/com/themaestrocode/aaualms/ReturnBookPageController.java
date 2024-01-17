package com.themaestrocode.aaualms;

import com.themaestrocode.aaualms.entity.Book;
import com.themaestrocode.aaualms.entity.User;
import com.themaestrocode.aaualms.service.BookService;
import com.themaestrocode.aaualms.service.UserService;
import com.themaestrocode.aaualms.utility.UtilityMethods;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ReturnBookPageController implements Initializable {

    @FXML
    private AnchorPane borrowerFrame, bookFrame;
    @FXML
    private Label libraryIdValueLabel, nameValueLabel, matricNoOrStaffIdValueLabel, facultyValueLabel, departmentValueLabel, levelValueLabel, returnBookOutcomeLabel;
    @FXML
    private Label bookIdValueLabel, bookTitleValueLabel, authorValueLabel, bookStatusValueLabel, shelveNoValueLabel, publisherValueLabel;
    @FXML
    private TextField scanCardTextField, scanBookTextField;
    @FXML
    private ImageView borrowerImage, bookImage, scannerIcon;
    @FXML
    private Button confirmBookReturnButton;

    private Stage returnBookStage;
    private String userLibraryId, bookId;
    private User user = null;
    private Book book = null;

    private UtilityMethods utilityMethods = new UtilityMethods();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        confirmBookReturnButton.setOnMouseEntered(mouseEvent -> {
            utilityMethods.changeGreenButtonColor(confirmBookReturnButton);
        });

        confirmBookReturnButton.setOnMouseExited(mouseEvent -> {
            utilityMethods.reverseGreenButtonColor(confirmBookReturnButton);
        });

        borrowerFrame.setVisible(false);
        bookFrame.setVisible(false);
        scannerIcon.setVisible(false);
        scanCardTextField.setDisable(true);
        scanBookTextField.setDisable(true);
        scanCardTextField.setOpacity(0);
        scanBookTextField.setOpacity(0);
    }

    public void scanBook() {
        utilityMethods.setScanIconAnimation(scannerIcon, true);
        scanBookTextField.setDisable(false);
        scanBookTextField.requestFocus();

        // Remove existing listener to avoid multiple listeners
        scanBookTextField.textProperty().removeListener(scanBookTextFieldChangeListener);

        // Add a new listener
        scanBookTextField.textProperty().addListener(scanBookTextFieldChangeListener);
    }

    private final ChangeListener<String> scanBookTextFieldChangeListener = (observable, oldValue, newValue) -> {
        if(newValue != null && newValue.length() == 10) {
            bookId = newValue;

            if(bookId != null) {
                Platform.runLater(() -> {
                    BookService bookService = new BookService();

                    book = bookService.findBookById(bookId);

                    returnBookOutcomeLabel.requestFocus();
                    scanBookTextField.setDisable(true);
                    utilityMethods.setScanIconAnimation(scannerIcon, false);

                    if(book != null) {
                        utilityMethods.displayBookInFrame(book, bookImage, bookIdValueLabel, bookTitleValueLabel, authorValueLabel, bookStatusValueLabel, shelveNoValueLabel,
                                publisherValueLabel, bookFrame);
                    }
                    else {
                        bookFrame.setVisible(false);
                        Platform.runLater(() -> {
                            utilityMethods.showInformationAlert("Error", "Book not found!", "Book does not exist in the library database.");
                        });
                    }
                });
            }
        }
    };

    public void scanCard() {
        utilityMethods.setScanIconAnimation(scannerIcon, true);
        scanCardTextField.setDisable(false);
        scanCardTextField.requestFocus();

        // Remove existing listener to avoid multiple listeners
        scanCardTextField.textProperty().removeListener(scanCardTextFieldChangeListener);

        // Add a new listener
        scanCardTextField.textProperty().addListener(scanCardTextFieldChangeListener);
    }

    private final ChangeListener<String> scanCardTextFieldChangeListener = (observable, oldValue, newValue) -> {
        if(newValue != null && newValue.length() == 10) {
            userLibraryId = newValue;

            if(userLibraryId != null) {
                Platform.runLater(() -> {
                    UserService userService = new UserService();

                    user = userService.findUserByLibraryId(userLibraryId);

                    returnBookOutcomeLabel.requestFocus();
                    scanCardTextField.setDisable(true);
                    utilityMethods.setScanIconAnimation(scannerIcon, false);

                    if(user != null) {
                        utilityMethods.displayUserInFrame(user, borrowerImage, libraryIdValueLabel, nameValueLabel, matricNoOrStaffIdValueLabel, facultyValueLabel, departmentValueLabel,
                                levelValueLabel,borrowerFrame);
                    }
                    else {
                        borrowerFrame.setVisible(false);
                        Platform.runLater(() -> {
                            utilityMethods.showInformationAlert("Error", "User not found!", "The scanned card is not registered with any user.");
                        });
                    }
                });
            }
        }
    };

    public void confirmBookReturn() {
        if(user != null && book != null) {
            BookService bookService = new BookService();

            //checking if the book being returned is available. To return a book, it must not be available in the first place.
            boolean bookAvailable = bookService.checkBookAvailability(book);

            if(!bookAvailable) {
                //verifying if the returner is the same person who borrowed the book is the one returning it.
                boolean returnerIsBorrower = bookService.verifyReturner(user, book);
                boolean proceed = false;

                if(!returnerIsBorrower) {
                    proceed = utilityMethods.showConfirmationAlert("Confirmation Alert", "The returner is not the borrower!",
                            "Do you want to proceed with this return transaction?");
                }

                if(returnerIsBorrower || proceed) {
                    boolean returnSuccessful = bookService.returnBook(user, book);

                    if(returnSuccessful) {
                        utilityMethods.showInformationAlert("Notification", "Transaction successful!",
                                "Book successfully returned by " + user.getFirstName() + " " + user.getLastName());
                    }
                    else {
                        utilityMethods.showInformationAlert("Error", "Transaction could not be processed!",
                                "Please try again or lodge a complain via the CONTACT section if the error persists. You can also visit the HELP section to learn about possible causes and solutions.");
                    }
                }
            }
            else {
                utilityMethods.showInformationAlert("Error", "Transaction failed!", "The book being returned was not borrowed. Perhaps you wanted to issue the book?");
            }
        }
    }

    public Stage getReturnBookStage() {
        return returnBookStage;
    }

    public void setReturnBookStage(Stage returnBookStage) {
        this.returnBookStage = returnBookStage;
    }
}
