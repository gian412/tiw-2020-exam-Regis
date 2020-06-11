package it.polimi.tiw.bank.dao;

import it.polimi.tiw.bank.beans.Account;

import javax.swing.table.TableRowSorter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private final Connection connection;
    private final int id;

    public UserDAO(Connection connection, int id) {
        this.connection = connection;
        this.id = id;
    }

    public List<Account> findAccountsByUserId() throws SQLException {

        String query = "SELECT * FROM account WHERE id = ?";
        List<Account> accounts = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, this.id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Account account = new Account();
                    account.setId(resultSet.getInt("id"));
                    account.setBalance(resultSet.getLong("balance"));
                    account.setOwner(resultSet.getInt("owner"));
                    accounts.add(account);
                }
            }
        }
        return accounts;

    }

    public Account findAccountByAccountId( int accountId) throws SQLException {

        String query = "SELECT * FROM account WHERE id = ? AND owner = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, accountId);
            preparedStatement.setInt(2, this.id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) {
                    return null;
                }
                resultSet.next();
                Account account = new Account();
                account.setId(resultSet.getInt("id"));
                account.setBalance(resultSet.getLong("balance"));
                account.setOwner(resultSet.getInt("owner"));
                return account;
            }
        }

    }
}
