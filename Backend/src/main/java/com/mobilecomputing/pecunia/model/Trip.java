package com.mobilecomputing.pecunia.model;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

public class Trip {
    @Id
    private String tripId;
    private String tripName;
    private String currency;
    private Date startOfTrip;
    private Date endOfTrip;
    private List<String> tripParticipants;
    private List<String> transactions;
    private List<String> admins;

    public Trip(String tripName,String currency, Date startOfTrip, Date endOfTrip, List<String> tripParticipants,
                List<String> transactions, List<String> admins) {
        this.tripName = tripName;
        this.currency= currency;
        this.startOfTrip = startOfTrip;
        this.endOfTrip = endOfTrip;
        this.tripParticipants = tripParticipants;
        this.transactions = transactions;
        this.admins = admins;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public Date getStartOfTrip() {
        return startOfTrip;
    }

    public void setStartOfTrip(Date startOfTrip) {
        this.startOfTrip = startOfTrip;
    }

    public Date getEndOfTrip() {
        return endOfTrip;
    }

    public void setEndOfTrip(Date endOfTrip) {
        this.endOfTrip = endOfTrip;
    }

    public List<String> getTripParticipants() {
        return tripParticipants;
    }

    public void setTripParticipants(List<String> tripParticipants) {
        this.tripParticipants = tripParticipants;
    }

    public List<String> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<String> transactions) {
        this.transactions = transactions;
    }

    public List<String> getAdmins() {
        return admins;
    }

    public void setAdmins(List<String> admins) {
        this.admins = admins;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "tripId='" + tripId + '\'' +
                ", tripName='" + tripName + '\'' +
                ", currency='" + currency + '\'' +
                ", startOfTrip=" + startOfTrip +
                ", endOfTrip=" + endOfTrip +
                ", tripParticipants=" + tripParticipants.size() +
                ", transactions=" + transactions.size() +
                ", admins=" + admins +
                '}';
    }
}
