package com.mobilecomputing.pecunia.model;

import org.springframework.data.annotation.Id;

public class Transaction {
    @Id
    String transactionId;
    private User debtor; //Schuldner
    private User creditor; // Gläubiger
    private String currency;
    private double loan;

    public Transaction(User debtor, User creditor, String currency, double loan) {
        this.debtor = debtor;
        this.creditor = creditor;
        this.currency = currency;
        this.loan = loan;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public User getDebtor() {
        return debtor;
    }

    public void setDebtor(User debtor) {
        this.debtor = debtor;
    }

    public User getCreditor() {
        return creditor;
    }

    public void setCreditor(User creditor) {
        this.creditor = creditor;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getLoan() {
        return loan;
    }

    public void setLoan(double loan) {
        this.loan = loan;
    }
}
