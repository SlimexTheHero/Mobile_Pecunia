package com.mobilecomputing.pecunia.model;

import java.util.List;

/**
 * Wrapper for the Models
 */
public class CompleteTrip {
    private String tripId;
    private String tripName;
    private String currency;
    private String tripDuration;
    private List<User> tripParticipants;
    private List<Transaction> transactions;
    private List<String> admins;
    private String imageBase64String;

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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTripDuration() {
        return tripDuration;
    }

    public void setTripDuration(String tripDuration) {
        this.tripDuration = tripDuration;
    }

    public List<User> getTripParticipants() {
        return tripParticipants;
    }

    public void setTripParticipants(List<User> tripParticipants) {
        this.tripParticipants = tripParticipants;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<String> getAdmins() {
        return admins;
    }

    public void setAdmins(List<String> admins) {
        this.admins = admins;
    }

    public String getImageBase64String() {
        return imageBase64String;
    }

    public void setImageBase64String(String imageBase64String) {
        this.imageBase64String = imageBase64String;
    }
}
