package com.mobilecomputing.pecunia.repository;

import com.mobilecomputing.pecunia.model.Trip;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface TripRepository extends CrudRepository<Trip, String> {

}
