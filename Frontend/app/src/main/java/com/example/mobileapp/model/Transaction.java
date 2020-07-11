package com.example.mobileapp.model;

public class Transaction {
    String transactionId;
    private String debtor; //Schuldner
    private String creditor; // Gläubiger
    private String currency;
    private double loan;
    private String location;
    private String date;

    public Transaction(String debtor, String creditor, String currency, double loan, String location, String date) {
        this.debtor = debtor;
        this.creditor = creditor;
        this.currency = currency;
        this.loan = loan;
        this.location = location;
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getDebtor() {
        return debtor;
    }

    public void setDebtor(String debtor) {
        this.debtor = debtor;
    }

    public String getCreditor() {
        return creditor;
    }

    public void setCreditor(String creditor) {
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
