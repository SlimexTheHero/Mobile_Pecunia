package com.mobilecomputing.pecunia.security;

import com.mobilecomputing.pecunia.model.ApplicationUser;
import com.mobilecomputing.pecunia.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository){
        System.out.println("userDetails Service created");
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        System.out.println("loadbyUsername");
        Optional<ApplicationUser> user = userRepository.findById(new ObjectId(userEmail));
        if(user ==null){
            throw new UsernameNotFoundException(user.toString());
        }
        ApplicationUser applicationUser = user.get();
        return new User(applicationUser.getEMail(),applicationUser.getPassword(), Collections.emptyList());
    }
}
