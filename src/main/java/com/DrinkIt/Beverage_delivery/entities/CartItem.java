package com.DrinkIt.Beverage_delivery.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Document(collection = "CartItems")
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    private String cartItemId;
    @NonNull
    private int price;
    @NonNull
    private int quantity;
}
