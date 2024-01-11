package com.themaestrocode.aaualms.repository;

import com.themaestrocode.aaualms.entity.Book;
import com.themaestrocode.aaualms.utility.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRepository {

    private String bookID, title, author, imagePath, bookStatus, shelveNo, isbn, publisher, dateAdded;

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
                title = resultSet.getString("title");
                author = resultSet.getString("author");
                imagePath = resultSet.getString("image_path");
                bookStatus = resultSet.getString("book_status");
                shelveNo = resultSet.getString("shelve_no");
                isbn = resultSet.getString("isbn");
                publisher = resultSet.getString("publisher");
                dateAdded = resultSet.getString("date_added");

                book = new Book(bookID, title, author, imagePath, bookStatus, shelveNo, isbn, publisher, dateAdded);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return book;
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
