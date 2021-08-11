package com.readingisgood.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @NonNull
    private Long bookId;

    @NonNull
    private Integer quantity;
}
