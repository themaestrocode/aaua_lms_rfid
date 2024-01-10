package com.themaestrocode.aaualms;

import com.themaestrocode.aaualms.entity.User;
import com.themaestrocode.aaualms.utility.UtilityMethods;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class EntityDetailsController implements Initializable {

    @FXML
    private ImageView studentImage;
    @FXML
    private Label studentLibraryIdLabel, nameLabel, matricNoLabel, facultyLabel, departmentLabel, levelLabel, phoneNoLabel, emailLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void initData(User user) {
        UtilityMethods utilityMethods = new UtilityMethods();

        File imageFile = utilityMethods.normalizeFile(user.getImagePath());

        if(imageFile != null && imageFile.exists()) {
            try {
                Image image = new Image(imageFile.toURI().toString());
                studentImage.setImage(image);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        studentLibraryIdLabel.setText(user.getUserLibraryId());
        nameLabel.setText(user.getFirstName() + " " + user.getLastName());
        matricNoLabel.setText(user.getUserId());
        facultyLabel.setText(user.getFaculty());
        departmentLabel.setText(user.getDepartment());
        levelLabel.setText(user.getLevel());
        phoneNoLabel.setText(user.getPhoneNumber());
        emailLabel.setText(user.getEmail());
    }
}
