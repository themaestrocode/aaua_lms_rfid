package com.themaestrocode.aaualms.utility;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UtilityMethods {

    private String booksInitialDirectory = "C:\\Users\\VICTOR A. SODERU\\Documents\\BOOKS PHOTO ALBUM";
    private String usersInitialDirectory = "C:\\Users\\VICTOR A. SODERU\\Documents\\USERS PHOTO ALBUM";
    private String userEntityType = "user", bookEntityType = "book";

    public File normalizeFile(String filePath) {
        if(filePath == null) {
            return null;
        }
        // Normalize the path using Paths.get()
        Path normalizedPath = Paths.get(filePath);

        return normalizedPath.toFile();
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

    public void setTextFieldInvalidAttribute(TextField textField) {
        textField.setText("invalid or empty!");
        textField.setStyle("-fx-text-fill: #AA0000");
    }

    public void reverseTextFieldColor(TextField name) {
        name.setStyle("-fx-text-fill: black");
    }
}
