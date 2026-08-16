package com.DrinkIt.Beverage_delivery.repositories;

import com.DrinkIt.Beverage_delivery.entities.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartRepository extends MongoRepository<Cart, String> {

    public Optional<Cart> findByUserId(String id);

}
