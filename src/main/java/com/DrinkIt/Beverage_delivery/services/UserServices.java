package com.DrinkIt.Beverage_delivery.services;

import com.DrinkIt.Beverage_delivery.entities.User;
import com.DrinkIt.Beverage_delivery.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServices {

    @Autowired
    UserRepository userRepository;

    public void saveNewUser(User user){
        userRepository.save(user);
    }


    public User findUserByUsername(String username){
        return userRepository.findUserByUsername(username);
    }
}
