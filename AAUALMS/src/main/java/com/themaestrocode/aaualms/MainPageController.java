package com.themaestrocode.aaualms;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {

    @FXML
    private HBox hboxID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyCustomCSS();
    }

    private void applyCustomCSS() {
        if(hboxID != null) {
            hboxID.getStylesheets().add(getClass().getResource("/com/themaestrocode/css/styling.css").toExternalForm());
        }
//        if(loginButton != null) {
//            loginButton.getStylesheets().add(getClass().getResource("/com/themaestrocode/css/styling.css").toExternalForm());
//        }
    }
}
