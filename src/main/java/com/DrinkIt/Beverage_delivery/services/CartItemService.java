package com.DrinkIt.Beverage_delivery.services;

import com.DrinkIt.Beverage_delivery.entities.CartItem;
import com.DrinkIt.Beverage_delivery.repositories.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {

    @Autowired
    CartItemRepository cartItemRepository;

    public void save(CartItem cartItem){
        cartItemRepository.save(cartItem);
    }
}
