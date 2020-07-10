package com.example.mobileapp.model;

import java.util.Date;
import java.util.List;

public class Trip {
    private String tripId;
    private String tripName;
    private String tripDuration;
    private String currency;
    private List<String> tripParticipants;
    private List<String> transactions;
    private List<String> admins;
    private String imageBase64String;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getImageBase64String() {
        return imageBase64String;
    }

    public void setImageBase64String(String imageBase64String) {
        this.imageBase64String = imageBase64String;
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

    public String getTripDuration() {
        return tripDuration;
    }

    public void setTripDuration(String tripDuration) {
        this.tripDuration = tripDuration;
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
}
