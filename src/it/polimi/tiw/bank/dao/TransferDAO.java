package it.polimi.tiw.bank.dao;

import it.polimi.tiw.bank.beans.User;

import java.sql.Connection;
import java.sql.SQLException;

public class TransferDAO {

    private final Connection connection;
    private final int id;

    public TransferDAO(Connection connection, int id) {
        this.connection = connection;
        this.id = id;
    }

}
