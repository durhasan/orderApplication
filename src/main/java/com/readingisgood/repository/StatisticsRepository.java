package com.readingisgood.repository;

import com.readingisgood.dto.OrderReportDto;
import com.readingisgood.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

@Component
public class StatisticsRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<OrderReportDto> getOrderReport(Long customerId) {

        Aggregation agg = newAggregation(
                unwind("orderItems"),
                project("customerId","totalAmount").and("orderItems.quantity").as("booksCount")
                        .andExpression("month(createDate)").as("month"),
                match(Criteria.where("customerId").is(customerId)),
                group("month").sum("totalAmount").as("totalPurchasedAmount")
                        .sum("booksCount").as("totalBooksCount")
                        .count().as("totalOrderCount"),
                project("totalOrderCount","totalPurchasedAmount","totalBooksCount").and("month").previousOperation(),
                sort(ASC, "totalOrderCount")
        );

        AggregationResults<OrderReportDto> results = mongoTemplate.aggregate(agg,
                "order", OrderReportDto.class);
        List<OrderReportDto> result = results.getMappedResults();


        return result;
    }
}
