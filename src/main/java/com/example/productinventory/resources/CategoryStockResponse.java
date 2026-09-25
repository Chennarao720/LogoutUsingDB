package com.example.productinventory.resources;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryStockResponse {

    private String categoryName;
    private Long totalStock;
}
