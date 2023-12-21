package com.themaestrocode.aaualms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("loginScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Image appIcon = new Image("C:\\Users\\user\\themaestrocode\\aaua_lms_rfid\\AAUALMS\\images\\aaua.png");

        stage.setTitle("AAUA MANAGEMENT SYSTEM");
        stage.setScene(scene);
        stage.getIcons().add(appIcon);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}