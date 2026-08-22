package com.DrinkIt.Beverage_delivery.services;

import com.DrinkIt.Beverage_delivery.entities.User;
import com.DrinkIt.Beverage_delivery.repositories.UserRepository;
import com.DrinkIt.Beverage_delivery.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceDetailsImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtUtils jwtUtils;

    public UserDetails loadUserByUsername(String username){
        User user = userRepository.findByUsername(username);
        if(user!=null){
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
        }
        throw new UsernameNotFoundException("User not found");
    }
}
