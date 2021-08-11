package com.readingisgood.entity;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Document
public class Order {

    @Transient
    public static final String SEQUENCE_NAME = "order_sequence";

    @Id
    private long id;
    @NonNull
    private Long customerId;
    private double totalAmount;

    @CreatedDate
    private Date createDate;

    private Date updateDate;

    @NonNull
    private List<OrderItem> orderItems;
}
