package com.themaestrocode.aaualms;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class IssueBookPageController implements Initializable {

    @FXML
    private AnchorPane borrowerFrame, bookFrame;
    @FXML
    private Label libraryIdValueLabel, nameValueLabel, matricNoOrStaffIdValueLabel, facultyValueLabel, departmentValueLabel, levelValueLabel;
    @FXML
    private Label bookIdValueLabel, bookTitleValueLabel, authorValueLabel, bookStatusValueLabel, shelveNoValueLabel, publisherValueLabel;
    @FXML
    private ImageView borrowerImage, bookImage;

    private Stage issueBookStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        borrowerFrame.setVisible(false);
        bookFrame.setVisible(false);
    }

    public void scanCard() {

    }

    public void scanBook() {

    }

    public Stage getIssueBookStage() {
        return issueBookStage;
    }

    public void setIssueBookStage(Stage issueBookStage) {
        this.issueBookStage = issueBookStage;
    }
}
