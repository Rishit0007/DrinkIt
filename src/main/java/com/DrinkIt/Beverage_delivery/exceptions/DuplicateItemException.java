package com.DrinkIt.Beverage_delivery.exceptions;

public class DuplicateItemException extends RuntimeException {
    public DuplicateItemException(String itemName) {
        super("Item already exists : "+itemName);
    }
}
