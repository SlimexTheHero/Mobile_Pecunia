package com.mobilecomputing.pecunia.controller;

import com.mobilecomputing.pecunia.model.Transaction;
import com.mobilecomputing.pecunia.model.Trip;
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
@RequestMapping(value = "/transaction")
public class TransactionController {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    TripRepository tripRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/getTransactionById")
    public ResponseEntity getTransactionById(@RequestAttribute String id) {
        try {
            return ResponseEntity.ok(transactionRepository.findById(id).get());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction not found");
        }
    }

    @GetMapping("/getAllTransactionsByTrip")
    public ResponseEntity getAllTransactionsByTrip(@RequestParam String tripId) {
        try{
            return ResponseEntity.ok(tripRepository.findById(tripId).get().getTransactions());
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trip not found");
        }
    }

    @PostMapping("/addTransactionToTrip")
    public ResponseEntity addTransactionToTrip(@RequestBody Transaction transaction,@RequestParam String tripId){
        try{
            Trip trip = tripRepository.findById(tripId).get();
            String newTransId= transactionRepository.save(transaction).getTransactionId();
            trip.getTransactions().add(newTransId);
            return  ResponseEntity.ok(newTransId);
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trip not found");
        }

    }
}
