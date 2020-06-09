package it.polimi.tiw.bank.beans;

import java.util.Date;

public class Transfer {

    private int id;
    private Date date;
    private long amount;
    private int originAccount;
    private int destinationAccount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getOriginAccount() {
        return originAccount;
    }

    public void setOriginAccount(int originAccount) {
        this.originAccount = originAccount;
    }

    public int getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(int destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

}
