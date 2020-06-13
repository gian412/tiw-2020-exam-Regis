package it.polimi.tiw.bank.beans;

import java.util.Date;

public class Transfer {

    private int id;
    private Date date;
    private double amount;
    private int originAccount;
    private int destinationAccount;
    private String causal;

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
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

    public String getCausal() {
        return causal;
    }

    public void setCausal(String causal) {
        this.causal = causal;
    }
}
