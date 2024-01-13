package com.themaestrocode.aaualms;

import com.themaestrocode.aaualms.entity.Book;
import com.themaestrocode.aaualms.entity.User;
import com.themaestrocode.aaualms.service.BookService;
import com.themaestrocode.aaualms.utility.UtilityMethods;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class AddBookPageController implements Initializable {

    @FXML
    private Button addBookButton;
    @FXML
    private ImageView scanIcon;
    @FXML
    private Label photoUploadConfirmationLabel, scanTagConfirmationLabel, addBookOutcomeLabel;
    @FXML
    private TextField bookIdTextField, titleTextField, authorTextField, shelveNoTextField, isbnTextField, publisherTextField;

    private Stage addBookStage;
    private String bookId, imagePath;

    private UtilityMethods utilityMethods = new UtilityMethods();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scanIcon.setVisible(false);
        bookIdTextField.setOpacity(0);
    }

    /**
     * called by the uploadPhotoButton: to choose the photo of the book from the system.
     * Its file selection operation is performed by the uploadEntityImageFile method of the UtilityMethods class
     */
    public void uploadPhoto() {
        File bookImage = utilityMethods.uploadEntityImageFile("book", addBookStage);

        if(bookImage != null) {
            imagePath = bookImage.getAbsolutePath();
            System.out.println(imagePath);
            utilityMethods.setLabelAttribute(photoUploadConfirmationLabel, "-fx-text-fill: #006400", "Photo uploaded!");
            utilityMethods.setLabelAttribute(scanTagConfirmationLabel, "-fx-text-fill: black", "waiting for book to be scanned...");
        }
        else {
            utilityMethods.setLabelAttribute(photoUploadConfirmationLabel, "-fx-text-fill: #AA0000", "Photo not uploaded!");
        }
    }

    public void scanBookTag() {
        BookService bookService = new BookService();

        if(!bookService.validateImageUpload(imagePath)) {
            utilityMethods.setLabelAttribute(photoUploadConfirmationLabel, "-fx-text-fill: #AA0000", "Photo not yet uploaded!");
        }

        bookIdTextField.setDisable(false);

        utilityMethods.setScanIconAnimation(scanIcon, true);

        bookIdTextField.requestFocus();
        bookIdTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.length() == 10) {
                Book book = bookService.findBookById(newValue);

                if (book != null) {
                    Platform.runLater(() -> {
                        bookIdTextField.clear();
                        System.out.println("tag already registered!");
                        scanTagConfirmationLabel.setText("");
                        utilityMethods.showInformationAlert("Error", "Book tag registration failed", "This tag has already been registered with another book!");
                    });
                }
                else {
                    utilityMethods.setScanIconAnimation(scanIcon, false);
                    bookId = newValue;
                    bookIdTextField.setDisable(true);
                    scanTagConfirmationLabel.requestFocus();
                    System.out.println("tag not yet registered: " + newValue);
                    utilityMethods.setLabelAttribute(scanTagConfirmationLabel, "-fx-text-fill: #006400", "Book tag successfully scanned!");
                }
            }
        });
    }

    public void addBook() {
        try {
            BookService bookService = new BookService();

            boolean bookSaved = false;
            boolean isValid = bookService.validateBookDetails(bookId, titleTextField, authorTextField, imagePath, shelveNoTextField, isbnTextField, publisherTextField);

            if(isValid) {
                Book book = createBookObject();
                bookSaved = bookService.addNewBook(book);
            }

            if(bookSaved) {
                utilityMethods.showInformationAlert("Notification", "New Book Added", "Book successfully added to the library's database!");
                //attempt to close the stage after successfully saving the user
                utilityMethods.closeStage(addBookStage);
            }
            else {
                utilityMethods.setLabelAttribute(addBookOutcomeLabel, "-fx-text-fill: #AA0000", "User could not be saved!");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Book createBookObject() {
        return new Book(bookId, titleTextField.getText(), authorTextField.getText(), imagePath, shelveNoTextField.getText(), isbnTextField.getText(), publisherTextField.getText());
    }

    public void changeAddBookButtonColor() {
        addBookButton.setStyle("-fx-background-color: #113C14");
    }

    public void reverseAddBookButtonColor() {
        addBookButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #006400, #32CD32)");
    }

    public Stage getAddBookStage() {
        return addBookStage;
    }

    public void setAddBookStage(Stage addBookStage) {
        this.addBookStage = addBookStage;
    }
}
