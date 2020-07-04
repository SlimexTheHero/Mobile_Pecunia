package com.mobilecomputing.pecunia.model;

import org.springframework.data.annotation.Id;

public class Transaction {
    @Id
    private String transactionId;
    private String debtor; //Schuldner
    private String creditor; // Gläubiger
    private String currency; // vllt Enum?
    private double loan;
    private String transactionName;

    public Transaction(String debtor, String creditor, String currency, double loan,String transactionName) {
        this.debtor = debtor;
        this.creditor = creditor;
        this.currency = currency;
        this.loan = loan;
        this.transactionName=transactionName;
    }



    public String prettyToString(){
        return transactionName+": "+ creditor+" owes "+debtor+" "+ loan;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
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
