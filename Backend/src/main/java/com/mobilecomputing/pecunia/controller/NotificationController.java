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
        try{
            notificationRepository.findAll().forEach(notification -> {
                if(notification.getTripId()==null){
                    notification.setTripId("default");
                }
                if(notification.getUserId().equals(userId)){
                    response.add(notification);
                }
            });
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }catch (NoSuchElementException e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(response);
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

    @PostMapping("/createDeleteTransactionNotification")
    public ResponseEntity createDeleteTransactionNotification(@RequestBody Notification notification){
        notificationRepository.save(notification);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
