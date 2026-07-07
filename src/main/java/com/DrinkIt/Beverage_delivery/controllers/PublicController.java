package com.DrinkIt.Beverage_delivery.controllers;

import com.DrinkIt.Beverage_delivery.DTO.UserDTO;
import com.DrinkIt.Beverage_delivery.entities.User;
import com.DrinkIt.Beverage_delivery.services.UserServiceDetailsImpl;
import com.DrinkIt.Beverage_delivery.services.UserServices;
import com.DrinkIt.Beverage_delivery.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserServiceDetailsImpl userServiceDetailsImpl;

    @Autowired JwtUtils jwtUtils;


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDTO userDTO){
        User user =  new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        Optional<User> targetUser = Optional.ofNullable(userServices.findUserByUsername(userDTO.getUsername()));
        try{
            if(targetUser.isEmpty()) {
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO){
        try{
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());
            authenticationManager.authenticate
                    (new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
            UserDetails userDetails = userServiceDetailsImpl.loadUserByUsername(user.getUsername());
            String token = jwtUtils.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(token,HttpStatus.OK);
        }catch (Exception e){
            log.error(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
