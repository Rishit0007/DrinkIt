package com.DrinkIt.Beverage_delivery.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    private int price;
    private int initialStock;
    private String name;
    private String description;
    private String category;
}
