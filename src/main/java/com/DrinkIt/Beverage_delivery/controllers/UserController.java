package com.DrinkIt.Beverage_delivery.controllers;

import com.DrinkIt.Beverage_delivery.entities.User;
import com.DrinkIt.Beverage_delivery.services.UserServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServices userServices;

    @PutMapping("/change-username")
    public ResponseEntity<?> updateUsername(@RequestBody String username){
        Optional<User> targetUser = Optional.ofNullable(userServices.findUserByUsername(username));
        try{
            if(targetUser.isEmpty()) {

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
