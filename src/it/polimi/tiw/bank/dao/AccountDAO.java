package it.polimi.tiw.bank.dao;

import it.polimi.tiw.bank.beans.Transfer;
import it.polimi.tiw.bank.beans.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    private final Connection connection;
    private final int id;

    public AccountDAO(Connection connection, int id) {
        this.connection = connection;
        this.id = id;
    }

    public List<Transfer> findOutgoingTransfer() throws SQLException {

        String query = "SELECT * FROM transfer WHERE origin = ? ORDER BY date DESC";
        List<Transfer> transfers = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, this.id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Transfer transfer = new Transfer();
                    transfer.setId(resultSet.getInt("id"));
                    transfer.setDate(resultSet.getDate("date"));
                    transfer.setAmount(resultSet.getLong("amount"));
                    transfer.setOriginAccount(resultSet.getInt("origin"));
                    transfer.setDestinationAccount(resultSet.getInt("destination"));
                    transfer.setCausal(resultSet.getString("causal"));
                    transfers.add(transfer);
                }
            }
        }
        return transfers;

    }

    public List<Transfer> findIncomingTransfer() throws SQLException {

        String query = "SELECT * FROM transfer WHERE destination = ? ORDER BY date DESC";
        List<Transfer> transfers = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, this.id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Transfer transfer = new Transfer();
                    transfer.setId(resultSet.getInt("id"));
                    transfer.setDate(resultSet.getDate("date"));
                    transfer.setAmount(resultSet.getLong("amount"));
                    transfer.setOriginAccount(resultSet.getInt("origin"));
                    transfer.setDestinationAccount(resultSet.getInt("destination"));
                    transfer.setCausal(resultSet.getString("causal"));
                    transfers.add(transfer);
                }
            }
        }
        return transfers;

    }

    public void updateBalance(long balance) throws SQLException {

        String query = "UPDATE account SET balance = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, balance);
            preparedStatement.setInt(2, this.id);
        }

    }
}
