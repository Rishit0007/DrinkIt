package com.DrinkIt.Beverage_delivery.repositories;

import com.DrinkIt.Beverage_delivery.entities.CartItems;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.ObjectInput;

public interface CartItemRepository extends MongoRepository<CartItems, ObjectId> {
}
