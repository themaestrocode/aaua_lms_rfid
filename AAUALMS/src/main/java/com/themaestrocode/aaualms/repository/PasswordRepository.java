package com.themaestrocode.aaualms.repository;

import com.themaestrocode.aaualms.entity.Password;
import com.themaestrocode.aaualms.utility.DBConnector;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PasswordRepository {

    public boolean savePassword(String hashedPassword, String salt) {
        changeCurrentPasswordToOld(); //change the current password to 'old' to save a new one

        boolean result = false;
        String query = "INSERT INTO passwords(hashed_password, salt, password_status) VALUES(?, ?, 'CURRENT')";

        try (Connection connection = DBConnector.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, hashedPassword);
            statement.setString(2, salt);

            int rowInserted = statement.executeUpdate();

            if(rowInserted > 0) {
                result = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private void changeCurrentPasswordToOld() {
        String query = "UPDATE passwords SET password_status = 'OLD' WHERE password_status = 'CURRENT'";

        try (Connection connection = DBConnector.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Password getCurrentPassword() {
        Password password = null;

        String query = "SELECT * FROM passwords WHERE password_status = 'CURRENT'";

        try (Connection connection = DBConnector.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                int passwordId = resultSet.getInt("password_id");
                String hash = resultSet.getString("hashed_password");
                String salt = resultSet.getString("salt");
                String status = resultSet.getString("password_status");
                Timestamp dateAdded = resultSet.getTimestamp("date_created");

                password = new Password(passwordId, hash, salt, status, dateAdded);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return password;
    }

    private List<Password> getAllPasswords() {
        List<Password> allPasswords = new ArrayList<>();

        String query = "SELECT * FROM passwords";

        try (Connection connection = DBConnector.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                int passwordId = resultSet.getInt("password_id");
                String hash = resultSet.getString("hashed_password");
                String salt = resultSet.getString("salt");
                String status = resultSet.getString("password_status");
                Timestamp dateAdded = resultSet.getTimestamp("date_created");

                Password password = new Password(passwordId, hash, salt, status, dateAdded);
                allPasswords.add(password);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allPasswords;
    }

    public Password findPassword(String passwordToFind) {
        Password password = null;

        for(Password pword: getAllPasswords()) {
            boolean passwordMatch = BCrypt.checkpw(passwordToFind, pword.getHash());

            if(passwordMatch) {
                password = pword;
                break;
            }
        }
        return password;
    }

}
