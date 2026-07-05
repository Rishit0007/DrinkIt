package com.DrinkIt.Beverage_delivery.controllers;

import com.DrinkIt.Beverage_delivery.DTO.UserDTO;
import com.DrinkIt.Beverage_delivery.entities.User;
import com.DrinkIt.Beverage_delivery.services.UserServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserServices userServices;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDTO userDTO){
        User user =  new User();
        user.setUsername(userDTO.getUsername());
        Optional<User> targerUser = Optional.ofNullable(userServices.findUserByUsername(userDTO.getUsername()));
        try{
            if(targerUser.isEmpty()) {
                userServices.saveNewUser(user);
                return new ResponseEntity<>(HttpStatus.OK);

            }
            else{
                System.out.println("User already Exists");
                return new ResponseEntity<>(HttpStatus.CONFLICT);

            }
        }catch (Exception e){
            log.error(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
