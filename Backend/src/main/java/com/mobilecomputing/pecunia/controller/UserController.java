package com.mobilecomputing.pecunia.controller;

import com.mobilecomputing.pecunia.model.User;
import com.mobilecomputing.pecunia.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user", method = RequestMethod.GET)
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/getById")
    public String getUserById(@RequestParam String id){
        return String.valueOf(userRepository.findById(new ObjectId(id)));
    }

    @GetMapping("/getAll")
    public String getAllUsers(){
        String response ="";
        userRepository.findAll().forEach(user -> {
            System.out.println(user.toString());
        });
        return String.valueOf(userRepository.findAll());
    }

    @PostMapping("/registrateUser")
    public String registrateUser(@RequestParam String eMail, @RequestParam String name, @RequestParam String surname,
                                 @RequestParam String password){
        User user = new User();
        user.seteMail(eMail); // überprüfen ob email schon vergeben ist
        user.setName(name);
        user.setSurname(surname);
        user.setPassword(password);

        userRepository.save(user);
        return "ok?";
    }

    @DeleteMapping("/deleteUser")
    public void deleteUser(@RequestParam String eMail){
        userRepository.deleteById(new ObjectId(eMail));
    }
}
