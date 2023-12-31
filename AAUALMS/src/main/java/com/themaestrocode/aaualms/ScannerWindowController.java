package com.themaestrocode.aaualms;

import com.themaestrocode.aaualms.service.UserService;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ScannerWindowController implements Initializable {

    private Stage stage;

    @FXML
    private ImageView scanImage;

    public TextField getCardOrTagValueTextField() {
        return cardOrTagValueTextField;
    }

    public void setCardOrTagValueTextField(TextField cardOrTagValueTextField) {
        this.cardOrTagValueTextField = cardOrTagValueTextField;
    }

    @FXML
    private TextField cardOrTagValueTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cardOrTagValueTextField.setVisible(true);

        RotateTransition rotateTransition = new RotateTransition();

        rotateTransition.setNode(scanImage);
        rotateTransition.setDuration(Duration.millis(3000));
        rotateTransition.setCycleCount(TranslateTransition.INDEFINITE);
        rotateTransition.setInterpolator(Interpolator.LINEAR);
        rotateTransition.setByAngle(360);
        rotateTransition.play();

        //validateCardOrTag("user");
    }

    public void loadScannerWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("scannerWindow.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        String css = this.getClass().getResource("/com/themaestrocode/css/styling.css").toExternalForm();
        scene.getStylesheets().add(css);

        Image scanIcon = new Image(getClass().getResourceAsStream("/com/themaestrocode/images/scan icon.png"));

        Stage scannerStage = new Stage();
        scannerStage.initModality(Modality.APPLICATION_MODAL);
        scannerStage.setTitle("Scanner Window");
        scannerStage.setScene(scene);
        scannerStage.getIcons().add(scanIcon);
        scannerStage.setResizable(false);
        scannerStage.show();
    }

    public boolean validateCardOrTag(String userOrBook) {
        final boolean[] result = {false};
        final String[] getUserFound = new String[1];

        ChangeListener<String> readerListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String cardOrTagId = cardOrTagValueTextField.getText();
                System.out.println(cardOrTagId);

                if(userOrBook.equals("user")) {

                    boolean userFound = false;

                    if(!cardOrTagId.isEmpty()) {
                        UserService userService = new UserService();

                        userFound = userService.findUser(cardOrTagId);
                    }

                    if(!userFound) {
                        result[0] = true;
                    }
                }
                else if(userOrBook.equals("book")) {

                }
            }
        };
        return result[0];
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
