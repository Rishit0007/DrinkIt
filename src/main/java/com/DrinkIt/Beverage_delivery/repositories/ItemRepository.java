package com.DrinkIt.Beverage_delivery.repositories;

import com.DrinkIt.Beverage_delivery.entities.Item;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ItemRepository extends MongoRepository<Item, String> {
    public Optional<Item> findById(String itemId);
    public Optional<Item> findByItemName(String itemName);

}
