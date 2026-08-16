package com.DrinkIt.Beverage_delivery.services;

import com.DrinkIt.Beverage_delivery.entities.Cart;
import com.DrinkIt.Beverage_delivery.entities.CartItem;
import com.DrinkIt.Beverage_delivery.entities.Item;
import com.DrinkIt.Beverage_delivery.exceptions.*;
import com.DrinkIt.Beverage_delivery.repositories.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CartServices {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    ItemServices itemServices;

    public void addToCart(String userId, String itemId, int quantity){


        Cart cart = cartRepository.findByUserId(userId).orElseGet(()->{
            log.warn("No cart found for existing user{}, crating one now",userId);
            Cart userCart = new Cart();
            userCart.setUserId(userId);
            cartRepository.save(userCart);
            return userCart;
        });

        Item item =itemServices.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));

        if(item.getStockLeft()<quantity){
            throw new InsufficientStockException(itemId);
        }

        Optional<CartItem> cartItem = cart.getCartItems().stream()
                .filter(cItem
                        -> cItem
                        .getCartItemId()
                        .equals(itemId))
                .findFirst();

        if(cartItem.isPresent()){
            cartItem.get().setQuantity(cartItem.get().getQuantity()+quantity);
        }else{
            cart.getCartItems().add(new CartItem(itemId,item.getPrice(),quantity));
        }

        cartRepository.save(cart);
    }

    public void removeFromCart(String userId,String itemId,int quantity) throws InvalidCartQuantityException, CartItemNotFoundException {

        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new CartNotFoundException(userId));


        Optional<CartItem> cartItem = cart.getCartItems().stream().filter(
                x -> x
                        .getCartItemId()
                        .equals(itemId))
                .findFirst();

        if(cartItem.isPresent()){
            CartItem existing = cartItem.get();
            if(existing.getQuantity() < quantity){
                throw new InvalidCartQuantityException("Cannot remove the more than what is added");
            }
            if(existing.getQuantity()<=0){
                throw new InvalidCartQuantityException("The quantity must be greater than zero");
            }
            if(existing.getQuantity() == quantity){
                cart.getCartItems().remove(existing);
            }
            else{
                cartItem.get().setQuantity(cartItem.get().getQuantity()-quantity);
            }
        }else{
            throw new CartItemNotFoundException(itemId);
        }

        cartRepository.save(cart);


    }


    public void saveCart(Cart cart){
        cartRepository.save(cart);
    }



    public void cartTotal(Cart cart){


    }






}
