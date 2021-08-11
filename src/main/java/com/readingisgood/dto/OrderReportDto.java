package com.readingisgood.dto;

import lombok.Data;

@Data
public class OrderReportDto {
    private String month;
    private Integer totalOrderCount;
    private Integer totalBooksCount;
    private double totalPurchasedAmount;
}
