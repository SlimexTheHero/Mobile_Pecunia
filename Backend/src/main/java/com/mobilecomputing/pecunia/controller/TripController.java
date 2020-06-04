package com.mobilecomputing.pecunia.controller;

import com.mobilecomputing.pecunia.repository.TripRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/trip", method = RequestMethod.GET)
public class TripController {

    @Autowired
    TripRepository tripRepository;

    @GetMapping("/getTripById")
    public String getTripById(@RequestParam String id){

        return String.valueOf(tripRepository.findById(id));
    }

    @GetMapping("/getAllTrips")
    public String getAllTrips(){
        tripRepository.findAll().forEach(trip->{
            System.out.println(trip.toString());
        });
        return "to do";
    }
}
