package com.DrinkIt.Beverage_delivery.services;

import com.DrinkIt.Beverage_delivery.entities.Cart;
import com.DrinkIt.Beverage_delivery.entities.User;
import com.DrinkIt.Beverage_delivery.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Service
public class UserServices {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartServices cartServices;
    public static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        User savedUser = userRepository.save(user);

        Cart cart = new Cart();
        cart.setUserId(savedUser.getUserId());
        cartServices.saveCart(cart);
        userRepository.save(savedUser);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
    public Optional<User> findById(String userId){
        return userRepository.findById(userId);
    }
}
