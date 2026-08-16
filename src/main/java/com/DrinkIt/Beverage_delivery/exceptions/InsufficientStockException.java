package com.DrinkIt.Beverage_delivery.exceptions;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String itemId) {
        super("Insufficient stock of item: "+itemId);
    }
}
