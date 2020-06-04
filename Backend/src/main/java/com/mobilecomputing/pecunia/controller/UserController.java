package com.mobilecomputing.pecunia.controller;

import com.mobilecomputing.pecunia.model.ApplicationUser;
import com.mobilecomputing.pecunia.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user", method = RequestMethod.GET)
public class UserController {
    @Autowired
    UserRepository userRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder;

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

    @PostMapping("/sign-up")
    public void signUp(@RequestBody ApplicationUser user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    @DeleteMapping("/deleteUser")
    public void deleteUser(@RequestParam String eMail){
        userRepository.deleteById(new ObjectId(eMail));
    }
}
