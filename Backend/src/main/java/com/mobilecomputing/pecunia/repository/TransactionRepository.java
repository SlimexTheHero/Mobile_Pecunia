package com.mobilecomputing.pecunia.repository;

import com.mobilecomputing.pecunia.model.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, String> {
}
