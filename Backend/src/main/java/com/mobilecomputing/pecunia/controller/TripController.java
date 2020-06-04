package com.mobilecomputing.pecunia.controller;

import com.mobilecomputing.pecunia.model.Trip;
import com.mobilecomputing.pecunia.model.User;
import com.mobilecomputing.pecunia.repository.TripRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/trip", method = RequestMethod.GET)
public class TripController {

    @Autowired
    TripRepository tripRepository;

    @GetMapping("/getTripById")
    public String getTripById(@RequestParam String id) {

        return String.valueOf(tripRepository.findById(id));
    }

    @GetMapping("/getAllTrips")
    public ArrayList<Trip> getAllTrips() {
        ArrayList<Trip> response = new ArrayList<>();
        tripRepository.findAll().forEach(trip -> {
            response.add(trip);
        });
        return response;
    }

    @PostMapping("/addTrip")
    public String addTrip(@RequestParam String tripName, @RequestParam String startOfTrip,
                          @RequestParam String endOfTrip, @RequestParam List<User> tripParticipants) {
        Trip temp = new Trip(tripName, null, null, tripParticipants, null);
        tripRepository.save(temp).getTripId();
        return tripRepository.save(temp).getTripId();
    }

    @GetMapping("/getTripByUser")
    public List<Trip> getTripsByUser(@RequestParam String eMail) {
        ArrayList<Trip> temp = new ArrayList<>();
        ArrayList<Trip> response = new ArrayList<>();
        tripRepository.findAll().forEach(trip -> {
            temp.add(trip);
        });
        for (Trip t : temp) {
            boolean eMailIsEqual = false;
            for (User u : t.getTripParticipants()) {
                if (u.geteMail().equals(eMail)) {
                    eMailIsEqual = true;
                    break;
                }
            }
            if (eMailIsEqual) {
                response.add(t);
                eMailIsEqual = false;
            }
        }
        return response;
    }
}
