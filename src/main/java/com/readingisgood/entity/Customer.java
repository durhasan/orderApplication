package com.readingisgood.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document
public class Customer {
    @Transient
    public static final String SEQUENCE_NAME = "customer_sequence";

    @Id
    private long id;
    private String name;
    private Address address;
    @Indexed(unique = true)
    private String email;
}
