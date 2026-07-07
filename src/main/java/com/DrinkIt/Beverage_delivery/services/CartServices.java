package com.DrinkIt.Beverage_delivery.services;

import com.DrinkIt.Beverage_delivery.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServices {

    @Autowired
    CartRepository cartRepository;


}
