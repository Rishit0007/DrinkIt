package com.DrinkIt.Beverage_delivery.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Items")
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    private String itemId;
    @NonNull
    private String itemName;
    @NonNull
    private String description;
    private int price;
    private int stockLeft;
}
