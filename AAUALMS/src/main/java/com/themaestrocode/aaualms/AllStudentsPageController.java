package com.themaestrocode.aaualms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AllStudentsPageController implements Initializable {

    @FXML
    private TableView<Integer, String, String, String, String, String, String, String> studentsTableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void loadAllStudentsPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("allStudentsPage.fxml"));
        ((Node) event.getSource()).getScene().setRoot(root);
    }


}
