package com.mobilecomputing.pecunia.repository;

import com.mobilecomputing.pecunia.model.ApplicationUser;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<ApplicationUser, ObjectId> {
}
