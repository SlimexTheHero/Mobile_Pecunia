package com.mobilecomputing.pecunia.controller;

import com.mobilecomputing.pecunia.model.Trip;
import com.mobilecomputing.pecunia.model.User;
import com.mobilecomputing.pecunia.repository.TripRepository;
import com.mobilecomputing.pecunia.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/user", method = RequestMethod.GET)
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TripRepository tripRepository;

    @GetMapping("/getByEMail")
    public String getUserByEmail(@RequestParam String eMail){
        return String.valueOf(userRepository.findById(eMail));
    }

    @GetMapping("/getAll")
    public List<User> getAllUsers(){
        ArrayList<User> response = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            response.add(user);
        });
        return response;
    }

    @PostMapping("/registrateUser")
    public String registrateUser(@RequestParam String eMail, @RequestParam String name, @RequestParam String surname,
                                 @RequestParam String password){

        if (userRepository.findById(eMail).isEmpty()) {

            User user = new User();
            user.seteMail(eMail); // überprüfen ob email schon vergeben ist
            user.setName(name);
            user.setSurname(surname);
            user.setPassword(password);

            userRepository.save(user);
            return "ok";
        }
        return "error";
    }

    @PostMapping("/addUserToTrip")
    public void addUserToTrip(@RequestParam String eMail,@RequestParam String tripId){
        Optional<Trip> t = tripRepository.findById(tripId);
        t.get().getTripParticipants().add(userRepository.findById(eMail).get());
    }

    @DeleteMapping("/deleteUser")
    public void deleteUser(@RequestParam String eMail){
        userRepository.deleteById(eMail);
    }
}
