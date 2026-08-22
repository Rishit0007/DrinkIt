package com.DrinkIt.Beverage_delivery.entities;

import com.DrinkIt.Beverage_delivery.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Items")
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    public Item(String itemName,String description,int price,int initialStock,Category category){
        this.itemName=itemName;
        this.description = description;
        this.price = price;
        this.stockLeft = initialStock;
        this.category = category;
        this.isActive = true;
    }

    @Id
    private String itemId;
    @NonNull
    private String itemName;
    @NonNull
    private String description;
    private int price;
    private int stockLeft;
    private Category category;
    private boolean isActive;
}
