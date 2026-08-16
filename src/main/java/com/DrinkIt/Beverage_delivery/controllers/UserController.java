package com.DrinkIt.Beverage_delivery.controllers;

import com.DrinkIt.Beverage_delivery.DTO.UserDTO;
import com.DrinkIt.Beverage_delivery.entities.User;
import com.DrinkIt.Beverage_delivery.services.UserServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServices userServices;

    @PutMapping("/change-username")
    public ResponseEntity<?> updateUsername(@RequestBody UserDTO userDTO){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userServices.findUserByUsername(username);
        try{
            if(userDTO.getUsername()!= null && !userDTO.getUsername().isEmpty()) {
                user.setUsername(userDTO.getUsername());
                userServices.saveUser(user);

                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                System.out.println("Invalid");
                return new ResponseEntity<>(HttpStatus.CONFLICT);

            }
        }catch (Exception e){
            log.error(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-address")
    public ResponseEntity<?> addAddress(@RequestBody UserDTO userDTO){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userServices.findUserByUsername(username);
        try{
            if(userDTO.getAddress()!= null && !userDTO.getAddress().isEmpty()) {
                user.setAddress(userDTO.getAddress());
                userServices.saveUser(user);

                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                System.out.println("Invalid");
                return new ResponseEntity<>(HttpStatus.CONFLICT);

            }
        }catch (Exception e){
            log.error(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-phone")
    public ResponseEntity<?> addPhone(@RequestBody UserDTO  userDTO){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userServices.findUserByUsername(username);
        try{
            if(userDTO.getPhoneNo()!=null && userDTO.getPhoneNo().length()==10) {
                user.setPhoneNo(userDTO.getPhoneNo());
                userServices.saveUser(user);

                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                System.out.println("Invalid");
                return new ResponseEntity<>(HttpStatus.CONFLICT);

            }
        }catch (Exception e){
            log.error(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
