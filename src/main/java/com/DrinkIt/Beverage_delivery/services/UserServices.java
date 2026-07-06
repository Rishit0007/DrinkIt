package com.DrinkIt.Beverage_delivery.services;

import com.DrinkIt.Beverage_delivery.entities.User;
import com.DrinkIt.Beverage_delivery.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;

@Service
public class UserServices {

    @Autowired
    UserRepository userRepository;

    public static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);
    }


    public User findUserByUsername(String username){
        return userRepository.findUserByUsername(username);
    }
}
