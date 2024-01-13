package com.themaestrocode.aaualms.service;

import com.themaestrocode.aaualms.entity.Book;
import com.themaestrocode.aaualms.repository.BookRepository;
import com.themaestrocode.aaualms.utility.UtilityMethods;
import javafx.scene.control.TextField;

import java.util.List;

public class BookService {

    private String addBookPageTextFieldsErrorMessage = "invalid or empty!";
    private BookRepository bookRepository = new BookRepository();

    public Book findBookById(String bookId) {
        return bookRepository.findBookById(bookId);
    }

    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public List<Book.IssuedBook> getAllBorrowedBooks() {
        return bookRepository.getAllBorrowedBooks();
    }

    public boolean addNewBook(Book book) {
        return bookRepository.addNewBook(book);
    }

    /**
     * checks every text field in the add book page window for invalid or empty entry and prints the appropriate error message
     * to the text field in red color. If the text field already has an error message in red color, on a new entry attempt,
     * it reverses the color back to black
     * @param bookId
     * @param title
     * @param author
     * @param imagePath
     * @param shelveNo
     * @param isbn
     * @param publisher
     * @return true or false
     */
    public boolean validateBookDetails(String bookId, TextField title, TextField author, String imagePath, TextField shelveNo, TextField isbn, TextField publisher) {
        if(validateBookId(bookId) && validateTitle(title.getText()) && validateAuthor(author.getText()) && validateImageUpload(imagePath) && validateShelveNo(shelveNo.getText()) &&
            validateIsbn(isbn.getText()) && validatePublisher(publisher.getText())) {
            return true;
        }

        UtilityMethods utilityMethods = new UtilityMethods();

        if (!validateTitle(title.getText()))
            utilityMethods.setTextFieldInvalidAttribute(title);
        else if (!title.getText().equals(addBookPageTextFieldsErrorMessage))
            utilityMethods.reverseTextFieldColor(title);

        if (!validateAuthor(author.getText()))
            utilityMethods.setTextFieldInvalidAttribute(author);
        else if (!author.getText().equals(addBookPageTextFieldsErrorMessage))
            utilityMethods.reverseTextFieldColor(author);

        if (!validateShelveNo(shelveNo.getText()))
            utilityMethods.setTextFieldInvalidAttribute(shelveNo);
        else if (!shelveNo.getText().equals(addBookPageTextFieldsErrorMessage))
            utilityMethods.reverseTextFieldColor(shelveNo);

        if (!validateIsbn(isbn.getText()))
            utilityMethods.setTextFieldInvalidAttribute(isbn);
        else if (!isbn.getText().equals(addBookPageTextFieldsErrorMessage))
            utilityMethods.reverseTextFieldColor(isbn);

        if (!validatePublisher(publisher.getText()))
            utilityMethods.setTextFieldInvalidAttribute(publisher);
        else if (!publisher.getText().equals(addBookPageTextFieldsErrorMessage))
            utilityMethods.reverseTextFieldColor(publisher);

        return false;
    }

    public String booksIssuedToday() {
        if(bookRepository.booksIssuedToday() == 0) {
            return "0";
        }
        return Integer.toString(bookRepository.booksIssuedToday());
    }

    public String booksDueToday() {
        int result = bookRepository.booksDueTodayForStudent() + bookRepository.booksDueTodayForStaff();

        if(result == 0) {
            return "0";
        }
        return Integer.toString(result);
    }

    public String availableBooks() {
        if(bookRepository.availableBooks() == 0) {
            return "0";
        }
        return Integer.toString(bookRepository.availableBooks());
    }

    public boolean validateImageUpload(String imagePath) {
        if(imagePath == null) {
            System.out.println("image not yet uploaded!");
            return false;
        }
        return true;
    }

    public boolean validateBookId(String bookId) {
        if(bookId.isEmpty() || bookId.equals(addBookPageTextFieldsErrorMessage)) {
            System.out.println("book id not validated");
            return false;
        }
        return true;
    }

    public boolean validateTitle(String title) {
        if(title.isEmpty() || title.equals(addBookPageTextFieldsErrorMessage)) {
            System.out.println("book title not validated");
            return false;
        }
        return true;
    }

    public boolean validateAuthor(String author) {
        if(author.isEmpty() || author.equals(addBookPageTextFieldsErrorMessage)) {
            System.out.println("author not validated");
            return false;
        }
        return true;
    }

    public boolean validateShelveNo(String shelveNo) {
        if(shelveNo.isEmpty() || shelveNo.equals(addBookPageTextFieldsErrorMessage)) {
            System.out.println("shelve no. not validated");
            return false;
        }
        return true;
    }

    public boolean validateIsbn(String isbn) {
        if(isbn.isEmpty() || isbn.equals(addBookPageTextFieldsErrorMessage)) {
            System.out.println("ISBN not validated");
            return false;
        }
        return true;
    }

    public boolean validatePublisher(String publisher) {
        if(publisher.isEmpty() || publisher.equals(addBookPageTextFieldsErrorMessage)) {
            System.out.println("publisher not validated");
            return false;
        }
        return true;
    }
}
