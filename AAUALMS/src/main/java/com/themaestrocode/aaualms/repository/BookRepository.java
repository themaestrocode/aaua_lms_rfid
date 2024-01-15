package com.themaestrocode.aaualms.repository;

import com.themaestrocode.aaualms.entity.Book;
import com.themaestrocode.aaualms.entity.User;
import com.themaestrocode.aaualms.utility.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    private String bookID, bookTitle, bookAuthor, imagePath, bookStatus, shelveNo, isbn, publisher, dateAdded;
    private int issueId;
    private String borrowerLibraryId, borrowerName, userType, issueDate, dueDate;

    public Book findBookById(String bookId) {
        Book book = null;

        try {
            Connection connection = DBConnector.connect();

            String query = "SELECT * FROM books WHERE book_id = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, bookId);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                bookID = resultSet.getString("book_id");
                bookTitle = resultSet.getString("title");
                bookAuthor = resultSet.getString("author");
                imagePath = resultSet.getString("image_path");
                bookStatus = resultSet.getString("book_status");
                shelveNo = resultSet.getString("shelve_no");
                isbn = resultSet.getString("isbn");
                publisher = resultSet.getString("publisher");
                dateAdded = resultSet.getString("date_added");

                book = new Book(bookID, bookTitle, bookAuthor, imagePath, bookStatus, shelveNo, isbn, publisher, dateAdded);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return book;
    }

    public List<Book> getAllBooks() {
        List<Book> libraryBooks = new ArrayList<>();
        Book book = null;

        try {
            Connection connection = DBConnector.connect();

            String query = "SELECT * FROM books";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                bookID = resultSet.getString("book_id");
                bookTitle = resultSet.getString("title");
                bookAuthor = resultSet.getString("author");
                imagePath = resultSet.getString("image_path");
                bookStatus = resultSet.getString("book_status");
                shelveNo = resultSet.getString("shelve_no");
                isbn = resultSet.getString("isbn");
                publisher = resultSet.getString("publisher");
                dateAdded = resultSet.getString("date_added");

                book = new Book(bookID, bookTitle, bookAuthor, imagePath, bookStatus, shelveNo, isbn, publisher, dateAdded);
                libraryBooks.add(book);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return libraryBooks;
    }

    public List<Book.IssuedBook> getAllBorrowedBooks() {
        List<Book.IssuedBook> borrowedBooks = new ArrayList<>();

        User user = null;
        Book book = null;
        Book.IssuedBook issuedBook = null;

        try {
            Connection connection = DBConnector.connect();

            String query = "SELECT * FROM issued_books";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                issueId = resultSet.getInt("issue_id");
                bookID = resultSet.getString("book_id");

                book = findBookById(bookID); //get the book using the id

                bookTitle = book.getTitle(); //get the book title using the book object
                borrowerLibraryId = resultSet.getString("student_lib_id");

                //if the borrower is not a student (i.e. the student_lib_id field is null), check the staff_lib_id field.
                if(borrowerLibraryId == null) {
                    borrowerLibraryId = resultSet.getString("staff_lib_id");
                }

                UserRepository userRepository = new UserRepository();
                user = userRepository.findUserByLibraryID(borrowerLibraryId); //get the user who borrowed the book by id

                borrowerName = user.getFirstName() + " " + user.getLastName();
                userType = resultSet.getString("user_type");
                issueDate = resultSet.getString("issue_date");
                dueDate = resultSet.getString("due_date");

                issuedBook = book.new IssuedBook(issueId, bookID, bookTitle, borrowerLibraryId, borrowerName, userType, issueDate, dueDate);
                borrowedBooks.add(issuedBook);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return borrowedBooks;
    }

    public boolean addNewBook(Book book) {
        boolean result = false;

        try {
            Connection connection = DBConnector.connect();

            String query = "INSERT INTO books(book_id, title, author, image_path, book_status, shelve_no, isbn, publisher) VALUES(?, ?, ?, ?, \"AVAILABLE\", ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, book.getBookId());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.setString(4, book.getImagePath());
            statement.setString(5, book.getShelveNo());
            statement.setString(6, book.getIsbn());
            statement.setString(7, book.getPublisher());

            int rowInserted = statement.executeUpdate();

            if (rowInserted > 0) {
                result = true;
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public boolean issueBook(User user, Book book) {
        try {
            Connection connection = DBConnector.connect();

            String query = "INSERT INTO issued_books(book_id, stud";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public int booksIssuedToday() {
        int result = 0;

        try {
            Connection connection = DBConnector.connect();

            String query = "SELECT COUNT(*) AS count_today FROM issued_books WHERE DATE(issue_date) = CURDATE()";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                result = resultSet.getInt("count_today");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public int booksDueTodayForStudent() {
        int result = 0;

        try {
            Connection connection = DBConnector.connect();

            String query = "SELECT COUNT(*) AS count_today FROM issued_books WHERE DATE_ADD(issue_date, INTERVAL 7 DAY) = CURDATE()";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                result = resultSet.getInt("count_today");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public int booksDueTodayForStaff() {
        int result = 0;

        try {
            Connection connection = DBConnector.connect();

            String query = "SELECT COUNT(*) AS count_today FROM issued_books WHERE DATE_ADD(issue_date, INTERVAL 14 DAY) = CURDATE()";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                result = resultSet.getInt("count_today");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public int availableBooks() {
        int result = 0;

        try {
            Connection connection = DBConnector.connect();

            String query = "SELECT COUNT(*) AS count_books FROM books WHERE book_status = \"AVAILABLE\"";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                result = resultSet.getInt("count_books");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
