package com.mobilecomputing.pecunia.controller;

import com.mobilecomputing.pecunia.model.Notification;
import com.mobilecomputing.pecunia.repository.NotificationRepository;
import com.mobilecomputing.pecunia.repository.TransactionRepository;
import com.mobilecomputing.pecunia.repository.TripRepository;
import com.mobilecomputing.pecunia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/notification")
public class NotificationController {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    TripRepository tripRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    NotificationRepository notificationRepository;

    @GetMapping("/getNotificationsFromUser")
    public ResponseEntity getNotificationsFromUser(@RequestParam String userId){
        ArrayList<Notification> response = new ArrayList<>();
        System.out.println("Angfrage gesendet");
        try{
            notificationRepository.findAll().forEach(notification -> {
                if(notification.getUserId().equals(userId)){
                    response.add(notification);
                }
            });
            return ResponseEntity.ok(response);

        }catch (NoSuchElementException e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addDeleteTransactionNotification")
    public ResponseEntity addDeleteTransaction(@RequestBody Notification notification){
        try{
            notificationRepository.save(notification);
        }catch (Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/deleteNotification")
    public ResponseEntity deleteNotification(@RequestParam String notificationId){
        try{
            notificationRepository.deleteById(notificationId);
            return ResponseEntity.ok(HttpStatus.OK);
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
