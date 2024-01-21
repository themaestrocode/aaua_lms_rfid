package com.themaestrocode.aaualms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginPageScene.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            String css = this.getClass().getResource("/com/themaestrocode/css/styling.css").toExternalForm();
            scene.getStylesheets().add(css);

            Image appIcon = new Image(getClass().getResourceAsStream("/com/themaestrocode/images/aaua.png"));

            stage.setTitle("AAUA LIBRARY MANAGEMENT SYSTEM");
            stage.setScene(scene);
            stage.getIcons().add(appIcon);
            //openingStageSize(stage);
            stage.setMaximized(true);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}