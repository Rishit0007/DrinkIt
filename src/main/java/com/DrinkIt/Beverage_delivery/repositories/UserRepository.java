package com.DrinkIt.Beverage_delivery.repositories;

import com.DrinkIt.Beverage_delivery.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);

    Optional<User> findById(String id);
}
