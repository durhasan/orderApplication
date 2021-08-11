package com.readingisgood.repository;

import com.readingisgood.dto.OrderReportDto;
import com.readingisgood.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, Long> {

    @Query("{customerId :?0}")
    List<Order> getOrderByCustomerId(Long customerId);

    @Query("{createDate : { $gte: ?0, $lt: ?1 }}")
    List<Order> getOrderBetweenDates(Date from, Date to);

}
