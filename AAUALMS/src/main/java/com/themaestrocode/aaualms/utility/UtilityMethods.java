package com.themaestrocode.aaualms.utility;

import com.themaestrocode.aaualms.entity.Book;
import com.themaestrocode.aaualms.entity.User;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UtilityMethods {

    private String booksInitialDirectory = "C:\\Users\\VICTOR A. SODERU\\Documents\\BOOKS PHOTO ALBUM";
    private String usersInitialDirectory = "C:\\Users\\VICTOR A. SODERU\\Documents\\USERS PHOTO ALBUM";
    private String userEntityType = "user", bookEntityType = "book";
    private static String email, password, filePath = "C:\\Users\\VICTOR A. SODERU\\Documents\\My Files\\email details.txt";


    public File normalizeFile(String filePath) {
        if(filePath == null) {
            return null;
        }
        // Normalize the path using Paths.get()
        Path normalizedPath = Paths.get(filePath);

        return normalizedPath.toFile();
    }

    private void fetchEmailDetails() throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));

        try {
            email = bufferedReader.readLine();
            password = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendBookIssueConfirmationEmail(String recipientEmail, String borrowerName, String bookTitle) throws FileNotFoundException {
        fetchEmailDetails();

        EmailSender emailSender = new EmailSender(email, password);

        String subject = "AAUA Library notification";
        String body = String.format("Hello %s.\n\nYou are receiving this email as a reminder of the time the book you just borrowed, \"%s\" will be due for return" +
                "Students: 7 days\nStaff: 14 days.\n\nThank you!", borrowerName, bookTitle);
        emailSender.sendEmail(recipientEmail, subject, body);
    }

    public void sendNewUserWelcomeMessage(String recipientEmail, String username) throws FileNotFoundException {
        fetchEmailDetails();

        EmailSender emailSender = new EmailSender(email, password);

        String subject = "AAUA Library notification";
        String body = String.format("Welcome, %s.\n\nCongratulations on registering as a library patron! Always remember to come along with your library card when visiting the library." +
                "See you around!", username);

        emailSender.sendEmail(recipientEmail, subject, body);
    }

    /**
     * opens a FileChooser dialog to choose the photo of a user or image of a book to be added.
     * @param entityType a string argument to determine whether a book or user image is to be uploaded.
     * @param stage specifies the stage upon which the FileChooser dialog is to be opened.
     * @return selected file
     */
    public File uploadEntityImageFile(String entityType, Stage stage) {
        FileChooser fileChooser = new FileChooser();

        if(entityType.equals(userEntityType)) {
            fileChooser.setTitle("Upload User Photo");
            //setting the initial directory where the file chooser opens by default
            File initialDirectory = new File(usersInitialDirectory);
            fileChooser.setInitialDirectory(initialDirectory);
        }
        else if(entityType.equals(bookEntityType)) {
            fileChooser.setTitle("Upload Book Image");
            //setting the initial directory where the file chooser opens by default
            File initialDirectory = new File(booksInitialDirectory);
            fileChooser.setInitialDirectory(initialDirectory);
        }

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG files", "*.jpg"));

        return fileChooser.showOpenDialog(stage);
    }

    public void displayUserInFrame(User user, ImageView borrowerImage, Label libraryIdValueLabel, Label nameValueLabel, Label matricNoOrStaffIdValueLabel,
                                    Label facultyValueLabel, Label departmentValueLabel, Label levelValueLabel, AnchorPane borrowerFrame) {
        UtilityMethods utilityMethods = new UtilityMethods();

        File imageFile = utilityMethods.normalizeFile(user.getImagePath());

        if(imageFile != null && imageFile.exists()) {
            try {
                Image image = new Image(imageFile.toURI().toString());
                borrowerImage.setImage(image);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        libraryIdValueLabel.setText(user.getUserLibraryId());
        nameValueLabel.setText(user.getFirstName() + " " + user.getLastName());
        matricNoOrStaffIdValueLabel.setText(user.getUserId());
        facultyValueLabel.setText(user.getFaculty());
        departmentValueLabel.setText(user.getDepartment());
        levelValueLabel.setText(user.getLevel());

        borrowerFrame.setVisible(true);
    }

    public void displayBookInFrame(Book book, ImageView bookImage, Label bookIdValueLabel, Label bookTitleValueLabel, Label authorValueLabel, Label bookStatusValueLabel,
                                   Label shelveNoValueLabel, Label publisherValueLabel, AnchorPane bookFrame) {
        UtilityMethods utilityMethods = new UtilityMethods();

        File imageFile = utilityMethods.normalizeFile(book.getImagePath());

        if(imageFile != null && imageFile.exists()) {
            try {
                Image image = new Image(imageFile.toURI().toString());
                bookImage.setImage(image);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        bookIdValueLabel.setText(book.getBookId());
        bookTitleValueLabel.setText(book.getTitle());
        authorValueLabel.setText(book.getAuthor());
        bookStatusValueLabel.setText(book.getBookStatus());
        shelveNoValueLabel.setText(book.getShelveNo());
        publisherValueLabel.setText(book.getPublisher());

        bookFrame.setVisible(true);
    }

    /**
     * displays an alert of INFORMATION type on the screen
     * @param title
     * @param headerText
     * @param message
     */
    public void showInformationAlert(String title, String headerText, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * receives a stage as an argument and closes it
     * @param stage
     */
    public void closeStage(Stage stage) {
        if(stage != null) {
            try {
                stage.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * sets and rotate the scan icon or hides it based on the boolean value of the 'show' argument received.
     * @param image the scan icon image
     * @param show receives a boolean value that determines the visibility of the scan icon
     */
    public void setScanIconAnimation(ImageView image, boolean show) {
        RotateTransition rotate = new RotateTransition();

        if(show == true) {
            image.setVisible(true);

            rotate.setNode(image);
            rotate.setDuration(Duration.millis(3000));
            rotate.setCycleCount(TranslateTransition.INDEFINITE);
            rotate.setInterpolator(Interpolator.LINEAR);
            rotate.setByAngle(360);
            rotate.play();
        }
        else {
            rotate.stop();
            image.setVisible(false);
        }
    }

    public boolean showConfirmationAlert(String title, String headerText, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(message);

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        alert.getButtonTypes().setAll(yesButton, noButton);

        // Wait for the user's decision
        alert.showAndWait();

        return alert.getResult() == yesButton;
    }

    /**
     * sets text on a label in a specified color
     * @param name
     * @param color
     * @param text
     */
    public void setLabelAttribute(Label name, String color, String text) {
        name.setStyle(color);
        name.setText(text);
    }

    public void changeGreenButtonColor(Button button) {
        button.setStyle("-fx-background-color: #113C14");
    }

    public void reverseGreenButtonColor(Button button) {
        button.setStyle("-fx-background-color: linear-gradient(to bottom right, #006400, #32CD32)");
    }

    public void changeRedButtonColor(Button button) {
        button.setStyle("-fx-background-color: #6F1515");
    }

    public void reverseRedButtonColor(Button button) {
        button.setStyle("-fx-background-color: linear-gradient(to bottom right, #AA0000, #EF0107)");
    }

    public void setTextFieldInvalidAttribute(TextField textField) {
        textField.setText("invalid or empty!");
        textField.setStyle("-fx-text-fill: #AA0000");
    }

    public void reverseTextFieldColor(TextField name) {
        name.setStyle("-fx-text-fill: black");
    }
}
