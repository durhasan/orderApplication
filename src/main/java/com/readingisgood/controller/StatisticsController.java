package com.readingisgood.controller;

import com.readingisgood.dto.OrderReportDto;
import com.readingisgood.entity.Order;
import com.readingisgood.repository.OrderRepository;
import com.readingisgood.repository.StatisticsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
@RestController
@Slf4j
@RequestMapping(value = "api/stats", produces = { MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8" }, consumes = {
        MediaType.APPLICATION_JSON_VALUE})
public class StatisticsController {

    @Autowired
    private StatisticsRepository statsRepository;

    @GetMapping(consumes = {MediaType.ALL_VALUE})
    public List<OrderReportDto> getOrders(@RequestParam Long customerId){
        log.info("Listing order statistics of customer with id {}", customerId);
        List<OrderReportDto> orders = statsRepository.getOrderReport(customerId);
        return orders;
    }
}
