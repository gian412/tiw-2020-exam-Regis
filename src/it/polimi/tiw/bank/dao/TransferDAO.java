package it.polimi.tiw.bank.dao;

import it.polimi.tiw.bank.beans.Account;
import it.polimi.tiw.bank.beans.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransferDAO {

    private final Connection connection;

    public TransferDAO(Connection connection) {
        this.connection = connection;
    }

    public void createTransfer(long amount, int originId, int destinationId, String causal, long originBalance, long destinationBalance) throws SQLException {

        AccountDAO originDAO = new AccountDAO(this.connection, originId);
        AccountDAO destinationDAO = new AccountDAO(this.connection, destinationId);

        String query = "INSERT INTO transfer (amount, origin, destination, causal) VALUES(?, ?, ?, ?)";

        connection.setAutoCommit(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, amount);
            preparedStatement.setInt(2, originId);
            preparedStatement.setInt(3, destinationId);
            preparedStatement.setString(4, causal);
            preparedStatement.executeUpdate();

            originDAO.updateBalance(originBalance-amount);
            destinationDAO.updateBalance(destinationBalance+amount);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }

    }

}
