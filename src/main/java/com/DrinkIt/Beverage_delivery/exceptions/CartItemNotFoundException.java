package com.DrinkIt.Beverage_delivery.exceptions;

public class CartItemNotFoundException extends Throwable {
    public CartItemNotFoundException(String itemId) {
        System.out.println("No item in cart matches itemid: "+itemId);
    }
}
