package com.DrinkIt.Beverage_delivery.exceptions;

public class InvalidCartQuantityException extends Throwable {
    public InvalidCartQuantityException(String message) {
        super(message);
    }
}
