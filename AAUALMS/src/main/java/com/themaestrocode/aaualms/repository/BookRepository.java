package com.themaestrocode.aaualms.repository;

import com.themaestrocode.aaualms.entity.Book;
import com.themaestrocode.aaualms.entity.User;
import com.themaestrocode.aaualms.utility.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    public Book findBookById(String bookId) {
        Book book = null;

        try {
            Connection connection = DBConnector.connect();

            String query = "SELECT * FROM books WHERE book_id = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, bookId);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                String bookID = resultSet.getString("book_id");
                String bookTitle = resultSet.getString("title");
                String bookAuthor = resultSet.getString("author");
                String imagePath = resultSet.getString("image_path");
                String bookStatus = resultSet.getString("book_status");
                String shelveNo = resultSet.getString("shelve_no");
                String isbn = resultSet.getString("isbn");
                String publisher = resultSet.getString("publisher");
                Timestamp dateAdded = resultSet.getTimestamp("date_added");

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
                String bookID = resultSet.getString("book_id");
                String bookTitle = resultSet.getString("title");
                String bookAuthor = resultSet.getString("author");
                String imagePath = resultSet.getString("image_path");
                String bookStatus = resultSet.getString("book_status");
                String shelveNo = resultSet.getString("shelve_no");
                String isbn = resultSet.getString("isbn");
                String publisher = resultSet.getString("publisher");
                Timestamp dateAdded = resultSet.getTimestamp("date_added");

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
                int issueId = resultSet.getInt("issue_id");
                String bookID = resultSet.getString("book_id");

                book = findBookById(bookID); //get the book using the id

                String bookTitle = book.getTitle(); //get the book title using the book object
                String borrowerLibraryId = resultSet.getString("student_lib_id");

                //if the borrower is not a student (i.e. the student_lib_id field is null), check the staff_lib_id field.
                if(borrowerLibraryId == null) {
                    borrowerLibraryId = resultSet.getString("staff_lib_id");
                }

                UserRepository userRepository = new UserRepository();
                user = userRepository.findUserByLibraryID(borrowerLibraryId); //get the user who borrowed the book by id

                String borrowerName = user.getFirstName() + " " + user.getLastName();
                String userType = resultSet.getString("user_type");
                Timestamp issueDate = resultSet.getTimestamp("issue_date");
                Timestamp dueDate = resultSet.getTimestamp("due_date");

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

            String query = "INSERT INTO books (book_id, title, author, image_path, book_status, shelve_no, isbn, publisher) VALUES (?, ?, ?, ?, 'AVAILABLE', ?, ?, ?)";

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
                EventRepository eventRepository = new EventRepository();

                if(eventRepository.logEventForBookRegistration(book)) {
                    result = true;
                }
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean issueBook(User user, Book book) {
        UserRepository userRepository = new UserRepository();

        boolean result = false;
        boolean isStudent = false;
        String semiQuery, userType;
        Timestamp dueDate;

        User userToPass = userRepository.findStudentByLibraryId(user.getUserLibraryId());

        if(userToPass.getUserLibraryId().equals(user.getUserLibraryId())) {isStudent = true;}

        if(isStudent) {
            userType = "STUDENT";
            dueDate = calculateDueDate(7);
            semiQuery = "INSERT INTO issued_books (book_id, student_lib_id, user_type, due_date) ";
        }
        else {
            userType = "STAFF";
            dueDate = calculateDueDate(14);
            semiQuery = "INSERT INTO issued_books (book_id, staff_lib_id, user_type, due_date) ";
        }

        String query = semiQuery + "VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnector.connect();
            PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, book.getBookId());
            statement.setString(2, userToPass.getUserLibraryId());
            statement.setString(3, userType);
            statement.setTimestamp(4, dueDate);

            int rowInserted = statement.executeUpdate();

            if(rowInserted > 0) {
                if(updateBookStatus(book)) { // changes the book status to ISSUED
                    EventRepository eventRepository = new EventRepository();

                    if(eventRepository.logEventForBookIssue(user, book)) { // logs the event in the event_history table
                        result = true;
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private boolean updateBookStatus(Book book) {
        boolean result = false;
        String bookStatus = book.getBookStatus();
        String query = null;

        if(bookStatus.equals("AVAILABLE")) query = "UPDATE books SET book_status = 'ISSUED' WHERE book_id = ?";
        else if(bookStatus.equals("ISSUED")) query = "UPDATE books SET book_status = 'AVAILABLE' WHERE book_id = ?";

        try {
            Connection connection = DBConnector.connect();

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, book.getBookId());

            int rowsUpdated = statement.executeUpdate();

            if(rowsUpdated > 0) result = true;

            statement.close();
            connection.close();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean checkBookAvailability(Book book) {
        boolean result = false;

        try {
            Connection connection = DBConnector.connect();

            String query = "SELECT book_id FROM books where book_id = ? AND book_status = 'AVAILABLE'";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, book.getBookId());

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                result = true;
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
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

    private Timestamp calculateDueDate(int daysToAdd) {
        long currentTimeMillis = System.currentTimeMillis();
        long dueDateTimeMillis = currentTimeMillis + daysToAdd * 24 * 60 * 60 * 1000;
        return new Timestamp(dueDateTimeMillis);
    }

}
