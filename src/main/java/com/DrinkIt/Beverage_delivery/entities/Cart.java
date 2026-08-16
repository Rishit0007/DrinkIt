package com.DrinkIt.Beverage_delivery.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "Carts")
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    private String cartId;
    @NonNull
    private String userId;
    List<CartItem> cartItems = new ArrayList<>();
}
