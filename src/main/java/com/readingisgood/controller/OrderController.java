package com.readingisgood.controller;

import com.readingisgood.entity.Book;
import com.readingisgood.entity.Order;
import com.readingisgood.entity.OrderItem;
import com.readingisgood.exception.BadRequestException;
import com.readingisgood.exception.ResourceNotFoundException;
import com.readingisgood.repository.BookRepository;
import com.readingisgood.repository.OrderRepository;
import com.readingisgood.service.SequenceGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping(value = "api/order", produces = { MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8" }, consumes = {
        MediaType.APPLICATION_JSON_VALUE})
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;


    @GetMapping(value = "/list", consumes = {MediaType.ALL_VALUE})
    public List<Order> getOrders(@RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") Date startDate, @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") Date endDate){
        log.info("Listing orders between dates {},{}",startDate, endDate);
        //set end of the day for endDate range
        Calendar cal =Calendar.getInstance();
        cal.setTime(endDate);
        cal.add(Calendar.DATE,1);
        return orderRepository.getOrderBetweenDates(startDate,cal.getTime());
    }

    @GetMapping(value = "/{id}", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<Optional<Order>> getOrderById(@PathVariable Long id){
        log.info("Listing orders with id{$1}",id);
        return ResponseEntity.ok(orderRepository.findById(id));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Order> addOrder(@RequestBody Order order) throws ResourceNotFoundException, BadRequestException {
        order.setId(sequenceGeneratorService.generateSequence(Order.SEQUENCE_NAME));
        order.setCreateDate(new Date());
        order.setUpdateDate(new Date());
        double totalAmount = 0;
        for (OrderItem item : order.getOrderItems()) {
            Optional<Book> book =  bookRepository.findById(item.getBookId());
            if(book.isEmpty())
                throw new BadRequestException("Book does not exists with id "+item.getBookId());
            else if(book.get().getStock() < item.getQuantity())
                throw new ResourceNotFoundException("Not enough stock for book "+book.get().getName());
            else {
                Book sold = book.get();
                totalAmount += item.getQuantity() * sold.getPrice();
                sold.setStock(sold.getStock()-item.getQuantity());
                bookRepository.save(sold);
            }
        }
        order.setTotalAmount(totalAmount);
        return ResponseEntity.ok(orderRepository.save(order));
    }

}
