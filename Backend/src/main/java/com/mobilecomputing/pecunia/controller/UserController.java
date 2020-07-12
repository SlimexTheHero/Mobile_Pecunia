package com.mobilecomputing.pecunia.controller;

import com.mobilecomputing.pecunia.model.Notification;
import com.mobilecomputing.pecunia.model.Trip;
import com.mobilecomputing.pecunia.model.User;
import com.mobilecomputing.pecunia.repository.NotificationRepository;
import com.mobilecomputing.pecunia.repository.TripRepository;
import com.mobilecomputing.pecunia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TripRepository tripRepository;
    @Autowired
    NotificationRepository notificationRepository;

    @GetMapping("/getByEMail")
    public ResponseEntity getUserByEmail(@RequestParam String eMail) {
        try{
            User response = userRepository.findById(eMail).get();
            if(response==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            return ResponseEntity.ok(response);
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping("/changeNameOfUser")
    public ResponseEntity changeNameOfUser(@RequestParam String eMail, @RequestParam String newName){
        try{
            User user = userRepository.findById(eMail).get();
            if(user==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            user.setName(newName);
            userRepository.save(user);
            return ResponseEntity.ok(HttpStatus.OK);
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
    public ResponseEntity addUserToTrip(@RequestParam String eMail, @RequestParam String tripId,
                                        @RequestBody Notification notification) {
        boolean userAlreadyExists = false;
        try{
            Trip trip = tripRepository.findById(tripId).get();
            User user = userRepository.findById(eMail).get();

            for (String currentUserEMail : trip.getTripParticipants()) {
                if (currentUserEMail.equals(eMail)) {
                    userAlreadyExists = true;
                }
            }

            if (userAlreadyExists) {
                return ResponseEntity.ok("User already exists");
            }
            if (trip == null || user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            trip.getTripParticipants().add(eMail);
            tripRepository.save(trip);
            notification.setNotificationMessage("You were added to the trip "+trip.getTripName());
            notification.setUserId(eMail);
            notification.setTripName(trip.getTripName());
            notificationRepository.save(notification);
            return ResponseEntity.status(HttpStatus.OK).body(user.getName());

        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Element not found");
        }
    }

    /**
     *
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

    @PostMapping("/addImg")
    public ResponseEntity addImg(@RequestParam String base64String,@RequestParam String eMail) throws IOException {
        System.out.println("Ist drinnen");
        FileOutputStream fos = new FileOutputStream("C:\\Users\\Bruno\\Desktop\\Mobile\\Backend\\src\\main\\resources");
        fos.write(Base64.getDecoder().decode(base64String));
        fos.close();
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/addImgToUser")
    public ResponseEntity addImgToUser(@RequestParam String base64String,@RequestParam String eMail) throws IOException {
        try{
            System.out.println("Ist drinnen");
            User user = userRepository.findById(eMail).get();
            if(user==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            user.setImageBase64String(base64String);
            userRepository.save(user);
            return ResponseEntity.ok(HttpStatus.OK);
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
