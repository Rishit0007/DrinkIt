package com.DrinkIt.Beverage_delivery.services;

import com.DrinkIt.Beverage_delivery.DTO.ItemDTO;
import com.DrinkIt.Beverage_delivery.entities.Item;
import com.DrinkIt.Beverage_delivery.enums.Category;
import com.DrinkIt.Beverage_delivery.exceptions.DuplicateItemException;
import com.DrinkIt.Beverage_delivery.exceptions.InsufficientStockException;
import com.DrinkIt.Beverage_delivery.exceptions.InvalidItemInfoException;
import com.DrinkIt.Beverage_delivery.exceptions.ItemNotFoundException;
import com.DrinkIt.Beverage_delivery.repositories.ItemRepository;
import io.jsonwebtoken.lang.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ItemServices {

    @Autowired
    ItemRepository itemRepository;

    public void listNewItem(ItemDTO itemDto){
        Optional<Item> item= itemRepository.findByItemName(itemDto.getName());
        if(item.isPresent()){
            throw new DuplicateItemException(item.get().getItemId());
        }


        validate(itemDto);

        boolean isCategoryValid = Arrays.asList(Category.values())
                .stream()
                .anyMatch(x->x.name()
                        .equalsIgnoreCase
                                (itemDto.getCategory()));

        if (!isCategoryValid){
            throw new InvalidItemInfoException("Invalid item category: "+itemDto.getCategory());
        }

        Category category = Category.valueOf(itemDto.getCategory().toUpperCase());

        Item newItem = new Item(itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getPrice(),
                itemDto.getInitialStock(),
                category);

        itemRepository.save(newItem);

    }

    // changes to be made later for this function -> add event driven method
//    only soft deleting the item right now
    public void removeItem(String itemId){
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
        item.setActive(false);
        itemRepository.save(item);
    }




    public int validateStock(String itemId){

        Item item = itemRepository.findById(itemId).orElseThrow(
                ()->new ItemNotFoundException(itemId));
        return item.getStockLeft();
    }


    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    public boolean itemExists(String itemId){
        Optional<Item> item = itemRepository.findById(itemId);
        if(item.isPresent()){
            return true;
        }
        return false;
    }


    public Optional<Item> findById(String itemId) {
        return itemRepository.findById(itemId);
    }

    public void save(Item item){
        itemRepository.save(item);
    }


    private void validate(ItemDTO dto) {
        if (isBlank(dto.getName())) {
            throw new InvalidItemInfoException("Name is required");
        }
        if (dto.getPrice() <= 0) {
            throw new InvalidItemInfoException("Price must be positive");
        }
        if (isBlank(dto.getDescription())) {
            throw new InvalidItemInfoException("Description is required");
        }

    }

    public void Restock(String itemId,int quantity){
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
        if(quantity <= 0){
            throw new InvalidItemInfoException("inavlid quantity "+ quantity);
        }
        item.setStockLeft(item.getStockLeft()+quantity);
        itemRepository.save(item);
    }

    public void decrementStock(String itemId,int quantity){
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
        if(quantity <= 0){
            throw new InvalidItemInfoException("inavlid quantity "+ quantity);
        }
        if(item.getStockLeft()<quantity){
            throw new InsufficientStockException(itemId);
        }
        item.setStockLeft(item.getStockLeft()-quantity);
        itemRepository.save(item);
    }

    public boolean isInStock(String itemId){
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
        if(item.getStockLeft()>0){
            return true;
        }
        return false;

    }

    public List<Item> getItemByCategory(String category){
        if(!validateCategory(category)) {
            throw new InvalidItemInfoException("Invalid category "+category);
        }

        return getAllItems().stream()
                .filter(
                        x -> x.getCategory().name().equalsIgnoreCase(category))
                .collect(Collectors.toCollection(ArrayList::new));
    }



    private void updateItem(String itemId,ItemDTO dto) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
        if (!isBlank(dto.getName())) {
            item.setItemName(dto.getName());
        }
        if (dto.getPrice() <= 0) {
            item.setPrice(dto.getPrice());
        }
        if (!isBlank(dto.getDescription())) {
            item.setDescription(dto.getDescription());
        }
        boolean isCategoryValid = Arrays.asList(Category.values())
                .stream()
                .anyMatch(
                        x -> x
                                .name()
                                .equalsIgnoreCase
                                        (dto.getCategory()));

        if (validateCategory(dto.getCategory())) {
            item.setCategory(Category.valueOf(dto.getCategory().toUpperCase()));
        }
    }
    public boolean validateCategory(String category){
            boolean isCategoryValid = Arrays.asList(Category.values())
                    .stream()
                    .anyMatch(
                            x-> x
                                    .name()
                                    .equalsIgnoreCase
                                            (category));

            return isCategoryValid;
        }



    private boolean isBlank(String s) {
        return s == null || s.isEmpty();
    }
}
