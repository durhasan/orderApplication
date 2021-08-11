package com.readingisgood.repository;

import com.readingisgood.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, Long> {
}
