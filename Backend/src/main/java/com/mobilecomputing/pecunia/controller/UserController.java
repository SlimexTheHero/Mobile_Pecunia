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
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TripRepository tripRepository;

    @GetMapping("/getByEMail")
    public ResponseEntity getUserByEmail(@RequestParam String eMail) {
        try{
            User response = userRepository.findById(eMail).get();
            return ResponseEntity.ok(response);
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllUsers() {
        ArrayList<User> response = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            response.add(user);
        });
        if (response.size() > 0) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @PostMapping("/registrateUser")
    public ResponseEntity registrateUser(@RequestBody User user) {

        if (userRepository.findById(user.geteMail()).isEmpty()) {
            userRepository.save(user);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already registered");
    }

    @PostMapping("/addUserToTrip")
    public ResponseEntity addUserToTrip(@RequestParam String eMail, @RequestParam String tripId) {
        boolean userAlreadyexists = false;
        try{
            Trip trip = tripRepository.findById(tripId).get();
            User user = userRepository.findById(eMail).get();

            for (String currentUserEMail : trip.getTripParticipants()) {
                if (currentUserEMail.equals(eMail)) {
                    userAlreadyexists = true;
                }
            }

            if (userAlreadyexists) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
            }
            if (trip == null || user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            trip.getTripParticipants().add(eMail);
            tripRepository.save(trip);

            return ResponseEntity.ok(HttpStatus.OK);

        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Element not found");
        }
    }

    /**
     * TODO User auch in den Gruppen löschen
     * @param eMail
     * @return
     */
    @DeleteMapping("/deleteUser")
    public ResponseEntity deleteUser(@RequestParam String eMail) {
        try{
            userRepository.findById(eMail).get();
            userRepository.deleteById(eMail);
            return ResponseEntity.ok(HttpStatus.OK);
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
