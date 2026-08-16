package com.DrinkIt.Beverage_delivery.exceptions;

public class ItemNotFoundException extends RuntimeException{

    public ItemNotFoundException(String itemId) {
        super("item not Found: "+itemId);
    }


}
