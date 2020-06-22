package com.mobilecomputing.pecunia.controller;

import com.mobilecomputing.pecunia.model.Trip;
import com.mobilecomputing.pecunia.model.User;
import com.mobilecomputing.pecunia.repository.TripRepository;
import com.mobilecomputing.pecunia.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/trip")
public class TripController {

    @Autowired
    TripRepository tripRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/getTripById")
    public ResponseEntity getTripById(@RequestParam String TripId) {
        try{
            return ResponseEntity.ok(tripRepository.findById(TripId).get());
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trip not found");
        }
    }

    @GetMapping("/getAllTrips")
    public ResponseEntity getAllTrips() {
        ArrayList<Trip> response = new ArrayList<>();
        tripRepository.findAll().forEach(trip -> {
            response.add(trip);
        });

        if(response.size()==0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addTrip")
    public ResponseEntity addTrip(@RequestBody Trip trip) {
        return ResponseEntity.ok(tripRepository.save(trip).getTripId());
    }

    @PostMapping("/addAdminToTrip")
    public ResponseEntity addAdminToTrip(@RequestParam String eMail,@RequestParam String TripId){
        try{
            tripRepository.findById(TripId).get().getAdmins().add(eMail);
            return ResponseEntity.ok(HttpStatus.OK);
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trip not found");
        }
    }

    @DeleteMapping("/deleteAdmin")
    public ResponseEntity deleteAdmin(@RequestParam String eMail, @RequestParam String TripId){
        try{
            tripRepository.findById(TripId).get().getAdmins().remove(eMail);
            return ResponseEntity.ok(HttpStatus.OK);
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trip not found");
        }
    }

    @GetMapping("/getTripsByUser")
    public ResponseEntity getTripsByUser(@RequestParam String eMail) {

        if(userRepository.findById(eMail).get()==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        //Eigentlich Datenbankabfrage
        ArrayList<Trip> temp = new ArrayList<>();
        ArrayList<Trip> response = new ArrayList<>();
        tripRepository.findAll().forEach(trip -> {
            temp.add(trip);
        });

        for (Trip t: temp) {
            boolean userIsParticipant = false;
            for(String tempEmail: t.getTripParticipants()){
                if(tempEmail.equals(eMail)){
                    userIsParticipant=true;
                }
            }

            if(userIsParticipant){
                response.add(t);
            }
        }

        if(response.size()==0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User has no trip");
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteTrip")
    public ResponseEntity deleteTrip(@RequestParam String TripId){
        try{
            tripRepository.findById(TripId).get();
            tripRepository.deleteById(TripId);
            return ResponseEntity.ok(HttpStatus.OK);
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trip not found");
        }
    }
}
