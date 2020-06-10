package it.polimi.tiw.bank.dao;

import it.polimi.tiw.bank.beans.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnonymousUserDAO {

    private final Connection connection;

    public AnonymousUserDAO(Connection connection) {
        this.connection = connection;
    }

    public User checkCredential(String username, String password) throws SQLException {

        String query = "SELECT * FROM user WHERE username = ? AND password = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) { // No result, no user with this ID
                    return null;
                }
                resultSet.next();
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("firstname"));
                user.setLastName(resultSet.getString("lastname"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        }

    }

    public User findUserById(int userId) throws SQLException {

        String query = "SELECT * FROM user WHERE  id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) { // No result, no user with this ID
                    return null;
                }
                resultSet.next();
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("firstname"));
                user.setLastName(resultSet.getString("lastname"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        }

    }

    public User findUserByUsername(String username) throws SQLException {

        String query = "SELECT * FROM user WHERE  username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) { // No result, no user with this ID
                    return null;
                }
                resultSet.next();
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("firstname"));
                user.setLastName(resultSet.getString("lastname"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        }

    }

    public User findUserByEmail(String email) throws SQLException {

        String query = "SELECT * FROM user WHERE  email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) { // No result, no user with this ID
                    return null;
                }
                resultSet.next();
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("firstname"));
                user.setLastName(resultSet.getString("lastname"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        }

    }

    public void createUser(String firstName, String lastName, String username, String email, String password) throws SQLException {

        String query = "INSERT INTO user (firstname, lastname, username, email, password) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, password);
            preparedStatement.executeUpdate();
        }
    }

}
