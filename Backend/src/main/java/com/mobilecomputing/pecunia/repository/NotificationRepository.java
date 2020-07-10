package com.mobilecomputing.pecunia.repository;

import com.mobilecomputing.pecunia.model.Notification;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification,String> {
}
