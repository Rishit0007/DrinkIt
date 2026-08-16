package com.DrinkIt.Beverage_delivery.exceptions;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(String userId) {
        super("Cart not found for user: "+userId);
    }
}
