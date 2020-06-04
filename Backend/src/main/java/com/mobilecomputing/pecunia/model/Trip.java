package com.mobilecomputing.pecunia.model;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

public class Trip {
    @Id
    private String tripId;
    private String tripName;
    private Date startOfTrip;
    private Date endOfTrip;
    private List<ApplicationUser> tripParticipants;

    public Trip(String tripId, String tripName, Date startOfTrip, Date endOfTrip, List<ApplicationUser> tripParticipants) {
        this.tripId = tripId;
        this.tripName = tripName;
        this.startOfTrip = startOfTrip;
        this.endOfTrip = endOfTrip;
        this.tripParticipants = tripParticipants;
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

    public List<ApplicationUser> getTripParticipants() {
        return tripParticipants;
    }

    public void setTripParticipants(List<ApplicationUser> tripParticipants) {
        this.tripParticipants = tripParticipants;
    }
}
