package com.DrinkIt.Beverage_delivery.services;

import com.DrinkIt.Beverage_delivery.entities.Cart;
import com.DrinkIt.Beverage_delivery.entities.CartItem;
import com.DrinkIt.Beverage_delivery.entities.Item;
import com.DrinkIt.Beverage_delivery.exceptions.*;
import com.DrinkIt.Beverage_delivery.repositories.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CartServices {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    ItemServices itemServices;
    @Autowired
    CartItemService cartItemService;

    public void addToCart(String userId, String itemId, int quantity){
        Cart cart = cartRepository.findByUserId(userId).orElseGet(()->{
            log.warn("No cart found for existing user{}, crating one now",userId);
            Cart userCart = new Cart();
            userCart.setUserId(userId);
            cartRepository.save(userCart);
            return userCart;
        });

        Optional<Item> item = itemServices.findById(itemId);
        if(item.isEmpty()){
            throw new ItemNotFoundException(itemId);
        }
        Item existing = item.get();
        if(itemServices.validateStock(itemId)<quantity){
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
            cart.getCartItems().add(new CartItem(itemId,existing.getPrice(),quantity));
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



    public int cartTotal(String userId){
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(()->new CartNotFoundException(userId));

        List<CartItem> items = new ArrayList<>(cart.getCartItems());

        if(items.isEmpty()){
            throw new CartIsEmptyException(userId);
        }

        int total = 0;

        for(CartItem item:items){
            total += item.getPrice() * item.getQuantity();
        }

        return total;

    }


    public List<CartItem> getItems(String userId){
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(()->new CartNotFoundException(userId));

        List<CartItem> items = new ArrayList<>(cart.getCartItems());


        if(items.isEmpty()){
            throw new CartIsEmptyException(userId);
        }

        return items;

    }

    public boolean hasItem(String userId,String itemId){

        Cart cart = cartRepository.findByUserId(userId).orElseThrow(()->new CartNotFoundException(userId));

        Optional<CartItem> item = cart.getCartItems().stream().filter(
                x->x.getCartItemId()
                        .equals(itemId))
                .findFirst();

        if(item.isEmpty()){
            return false;
        }

        return true;

    }


    public void updateQuantity(String userId,String itemId,int newQuantity) throws InvalidCartQuantityException {
        if (newQuantity <= 0) {
            throw new InvalidCartQuantityException(newQuantity + "is invalid quantity");
        }
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new CartNotFoundException(userId));

        Optional<CartItem> item = cart.getCartItems().stream().filter(
                        x -> x.getCartItemId()
                                .equals(itemId))
                .findFirst();

        if (item.isEmpty()) {
            addToCart(userId, itemId, newQuantity);
            return;
        }
        CartItem existing = null;
        if (item.isPresent()) {
            existing = item.get();
            int oldQuantity = existing.getQuantity();
            if (oldQuantity < newQuantity) {
                if (itemServices.validateStock(itemId) > newQuantity - oldQuantity) {
                    existing.setQuantity(newQuantity);

                } else {
                    throw new InsufficientStockException(itemId);
                }
            }


        }
        cartRepository.save(cart);


    }

//    check for item in cart ,if get currnt quantity and id its less than upqty add teh difference after validating stock and if its greater than upqty reduce the existing qyantity form cart and add teh remaining back to stock






}
