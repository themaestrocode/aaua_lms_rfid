package com.themaestrocode.aaualms.repository;

import com.themaestrocode.aaualms.entity.Book;
import com.themaestrocode.aaualms.entity.Event;
import com.themaestrocode.aaualms.entity.User;
import com.themaestrocode.aaualms.utility.DBConnector;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventRepository {

    private String bookIssueEvent = "BOOK ISSUE", bookReturnEvent = "BOOK RETURN", addUserEvent = "USER REGISTRATION", addBookEvent = "BOOK REGISTRATION";

    public List<Event> getTodaysEvents() {
        List<Event> todaysEvents = new ArrayList<>();

        String query = "SELECT * FROM event_history WHERE DATE(event_date) = ?";

        try (Connection connection = DBConnector.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Set the date to the current date without the time component
            LocalDate currentDate = LocalDate.now();
            Date currentDateSql = Date.valueOf(currentDate);

            statement.setDate(1, currentDateSql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int eventId = resultSet.getInt("event_id");
                String eventType = resultSet.getString("event_type");
                String bookId = resultSet.getString("book_id");
                String userLibraryId = resultSet.getString("user_library_id");
                Timestamp eventDate = resultSet.getTimestamp("event_date");

                Event event = new Event(eventId, eventType, bookId, userLibraryId, eventDate);
                todaysEvents.add(event);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return todaysEvents;
    }

    public boolean logEventForUserRegistration(User user) {
        boolean result = false;

        String query = "INSERT INTO event_history (event_type, user_library_id) VALUES (?, ?)";

        try (Connection connection = DBConnector.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, addUserEvent);
            statement.setString(2, user.getUserLibraryId());

            int rowInserted = statement.executeUpdate();

            if(rowInserted > 0) {
                result = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean logEventForBookRegistration(Book book) {
        boolean result = false;

        String query = "INSERT INTO event_history (event_type, book_id) VALUES (?, ?)";

        try (Connection connection = DBConnector.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, addBookEvent);
            statement.setString(2, book.getBookId());

            int rowInserted = statement.executeUpdate();

            if(rowInserted > 0) {
                result = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean logEventForBookIssue(User user, Book book) {
        boolean result = false;

        String query = "INSERT INTO event_history (event_type, book_id, user_library_id) VALUES (?, ?, ?)";

        try (Connection connection = DBConnector.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, bookIssueEvent);
            statement.setString(2, book.getBookId());
            statement.setString(3, user.getUserLibraryId());

            int rowInserted = statement.executeUpdate();

            if(rowInserted > 0) {
                result = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean logEventForBookReturn(User user, Book book) {
        boolean result = false;

        String query = "INSERT INTO event_history (event_type, book_id, user_library_id) VALUES (?, ?, ?)";

        try (Connection connection = DBConnector.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, bookReturnEvent);
            statement.setString(2, book.getBookId());
            statement.setString(3, user.getUserLibraryId());

            int rowInserted = statement.executeUpdate();

            if(rowInserted > 0) {
                result = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
