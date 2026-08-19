package com.DrinkIt.Beverage_delivery.exceptions;

public class CartIsEmptyException extends RuntimeException {
    public CartIsEmptyException(String userId) {
        super("Cart is empty - user : "+userId);
    }
}
