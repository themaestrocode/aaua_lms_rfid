package com.themaestrocode.aaualms.repository;

import com.themaestrocode.aaualms.entity.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRepository {

    public int booksIssuedToday() {
        int result = 0;

        try {
            Connection connection = DBConnector.connect();

            String query = "SELECT COUNT(*) AS count_today FROM issued_books WHERE DATE(date_issued) = CURDATE()";
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

            String query = "SELECT COUNT(*) AS count_today FROM issued_books WHERE DATE_ADD(date_issued, INTERVAL 7 DAY) = CURDATE()";
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

            String query = "SELECT COUNT(*) AS count_today FROM issued_books WHERE DATE_ADD(date_issued, INTERVAL 14 DAY) = CURDATE()";
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

            String query = "SELECT COUNT(*) AS count_books FROM book WHERE book_status = \"AVAILABLE\"";
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
