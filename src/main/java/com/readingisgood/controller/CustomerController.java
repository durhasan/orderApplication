package com.readingisgood.controller;

import com.readingisgood.entity.Book;
import com.readingisgood.entity.Customer;
import com.readingisgood.entity.Order;
import com.readingisgood.repository.BookRepository;
import com.readingisgood.repository.CustomerRepository;
import com.readingisgood.repository.OrderRepository;
import com.readingisgood.service.SequenceGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "api/customer", produces = { MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8" }, consumes = {
        MediaType.APPLICATION_JSON_VALUE})
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;


    @GetMapping(value = "/list", consumes = {MediaType.ALL_VALUE})
    public List<Customer> getCustomers(){
        log.info("Listing all customers");
        return customerRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer){
        customer.setId(sequenceGeneratorService.generateSequence(Customer.SEQUENCE_NAME));
        return ResponseEntity.ok(customerRepository.save(customer));
    }

    @GetMapping(value = "/listOrders/{customerId}", consumes = {MediaType.ALL_VALUE})
    public List<Order> getAllOrdersOfCustomers(@PathVariable Long customerId){
        log.info("Listing all orders of a customer");

        return orderRepository.getOrderByCustomerId(customerId);
    }
}
