package com.themaestrocode.aaualms;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.IOException;

public class InsightPageController {

    public void loadInsightPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("insightPage.fxml"));
        ((Node) event.getSource()).getScene().setRoot(root);
    }
}
