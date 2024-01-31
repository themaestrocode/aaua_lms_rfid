package com.themaestrocode.aaualms;

import com.themaestrocode.aaualms.entity.Book;
import com.themaestrocode.aaualms.entity.User;
import com.themaestrocode.aaualms.service.BookService;
import com.themaestrocode.aaualms.service.UserService;
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
    private ImageView entityImage;
    @FXML
    private Label label1Value, label2Value, label3Value, label4Value, label5Value, label6Value, label7Value, label8Value;
    @FXML
    private Label label1, label2, label3, label4, label5, label6, label7, label8;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        label3Value.setOnMouseClicked(mouseEvent -> {
            showBookDetailedView();
        });

        label5Value.setOnMouseClicked(mouseEvent -> {
            showBorrowerDetailedView();
        });
    }

    public void studentData(User student) {
        UtilityMethods utilityMethods = new UtilityMethods();

        File imageFile = utilityMethods.normalizeFile(student.getImagePath());

        if(imageFile != null && imageFile.exists()) {
            try {
                Image image = new Image(imageFile.toURI().toString());
                entityImage.setImage(image);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        label1.setText("Student Lib ID :");
        label2.setText("Name :");
        label3.setText("Matric no :");
        label4.setText("Faculty :");
        label5.setText("Department :");
        label6.setText("Phone No :");
        label7.setText("Email Address :");
        label8.setText("Level :");

        label1Value.setText(student.getUserLibraryId());
        label2Value.setText(student.getFirstName() + " " + student.getLastName());
        label3Value.setText(student.getUserId());
        label4Value.setText(student.getFaculty());
        label5Value.setText(student.getDepartment());
        label6Value.setText(student.getPhoneNumber());
        label7Value.setText(student.getEmail());
        label8Value.setText(student.getLevel());
    }

    public void staffData(User staff) {
        UtilityMethods utilityMethods = new UtilityMethods();

        File imageFile = utilityMethods.normalizeFile(staff.getImagePath());

        if(imageFile != null && imageFile.exists()) {
            try {
                Image image = new Image(imageFile.toURI().toString());
                entityImage.setImage(image);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        label1.setText("Staff Lib ID :");
        label2.setText("Name :");
        label3.setText("Staff ID :");
        label4.setText("Faculty :");
        label5.setText("Department :");
        label6.setText("Phone No :");
        label7.setText("Email Address :");
        label8.setVisible(false);

        label1Value.setText(staff.getUserLibraryId());
        label2Value.setText(staff.getFirstName() + " " + staff.getLastName());
        label3Value.setText(staff.getUserId());
        label4Value.setText(staff.getFaculty());
        label5Value.setText(staff.getDepartment());
        label6Value.setText(staff.getPhoneNumber());
        label7Value.setText(staff.getEmail());
        label8Value.setVisible(false);
    }

    public void bookData(Book book) {
        UtilityMethods utilityMethods = new UtilityMethods();

        File imageFile = utilityMethods.normalizeFile(book.getImagePath());

        if(imageFile != null && imageFile.exists()) {
            try {
                Image image = new Image(imageFile.toURI().toString());
                entityImage.setImage(image);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        label1.setText("Book Tag ID :");
        label2.setText("Title :");
        label3.setText("Author :");
        label4.setText("Book Status :");
        label5.setText("Shelve No :");
        label6.setText("ISBN :");
        label7.setText("Publisher :");
        label8.setText("Date Added :");

        label1Value.setText(book.getBookId());
        label2Value.setText(book.getTitle());
        label3Value.setText(book.getAuthor());
        label4Value.setText(book.getBookStatus());
        label5Value.setText(book.getShelveNo());
        label6Value.setText(book.getIsbn());
        label7Value.setText(book.getPublisher());
        label8Value.setText(book.getDateAdded().toString());
    }

    public void issuedBookData(Book.IssuedBook issuedBook) {
        UtilityMethods utilityMethods = new UtilityMethods();

        File imageFile = utilityMethods.normalizeFile(issuedBook.getImagePath());

        if(imageFile != null && imageFile.exists()) {
            try {
                Image image = new Image(imageFile.toURI().toString());
                entityImage.setImage(image);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        label1.setText("Issue ID :");
        label2.setText("Book Title :");
        label3.setText("Book Tag ID :");
        label4.setText("Borrower Name :");
        label5.setText("Borrower Lib ID :");
        label6.setText("Borrower Type :");
        label7.setText("Issued Date :");
        label8.setText("Due Date :");

        label1Value.setText(String.valueOf(issuedBook.getIssueId()));
        label2Value.setText(issuedBook.getBookTitle());
        label3Value.setText(issuedBook.getBookId());
        label4Value.setText(issuedBook.getBorrowerName());
        label5Value.setText(issuedBook.getBorrowerLibraryId());
        label6Value.setText(issuedBook.getUserType());
        label7Value.setText(issuedBook.getIssueDate().toString());
        label8Value.setText(issuedBook.getDueDate().toString());
    }

    public void showBookDetailedView() {
        BookService bookService = new BookService();

        Book book = bookService.findBookById(label3Value.getText());

        if(book != null) {
            label3Value.setStyle("-fx-text-fill: #0040ff");
            AllBooksPageController allBooksPageController = new AllBooksPageController();

            allBooksPageController.showDetailedView(book);
        }
    }

    public void showBorrowerDetailedView() {
        UserService userService = new UserService();

        User user = userService.findUserByLibraryId(label5Value.getText());

        if(user != null) {
            label5Value.setStyle("-fx-text-fill: #0040ff");
            if(label6Value.getText().equals("STUDENT")) {
                AllStudentsPageController allStudentsPageController = new AllStudentsPageController();

                allStudentsPageController.showDetailedView(user);
            }
            else if(label6Value.getText().equals("STAFF")) {
                AllStaffPageController allStaffPageController = new AllStaffPageController();

                allStaffPageController.showDetailedView(user);
            }
        }

    }
}
