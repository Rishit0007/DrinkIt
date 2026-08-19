package com.DrinkIt.Beverage_delivery.services;

import com.DrinkIt.Beverage_delivery.entities.Item;
import com.DrinkIt.Beverage_delivery.exceptions.ItemNotFoundException;
import com.DrinkIt.Beverage_delivery.repositories.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ItemServices {

    @Autowired
    ItemRepository itemRepository;

    public int validateStock(String itemId){

        Item item = itemRepository.findById(itemId).orElseThrow(()->new ItemNotFoundException(itemId));
        return item.getStockLeft();
    }

    public void save(Item item){
        itemRepository.save(item);
    }

    public Optional<Item> findById(String itemId) {
        return itemRepository.findById(itemId);
    }
}
