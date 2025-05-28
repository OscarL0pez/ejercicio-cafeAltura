package com.cafeteria.cafedealtura.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class OrderItemDTO {
    @NotNull(message = "Cafe ID is required")
    private Long cafeId;
    private String name; // Optional, can use cafe name
    private double price; // Optional, can use cafe price
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    public OrderItemDTO() {
    }

    public Long getCafeId() {
        return cafeId;
    }

    public void setCafeId(Long cafeId) {
        this.cafeId = cafeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}