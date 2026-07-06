package com.DrinkIt.Beverage_delivery.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "Users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private ObjectId userId;
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String email;
    private String address;
    private String phoneNo;
    private List<String> roles;

}
