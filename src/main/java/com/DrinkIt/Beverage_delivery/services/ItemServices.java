package com.DrinkIt.Beverage_delivery.services;

import com.DrinkIt.Beverage_delivery.entities.Item;
import com.DrinkIt.Beverage_delivery.repositories.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ItemServices {

    @Autowired
    ItemRepository itemRepository;

    public void listNewItem(Item item){
        try{
            if(!itemRepository.existsById(item.getItemId())){
                itemRepository.save(item);
            }
        }catch (Exception e){
            log.error(e.toString());
        }
    }
}
