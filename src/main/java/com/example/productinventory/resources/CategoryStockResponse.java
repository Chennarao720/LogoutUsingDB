package com.example.productinventory.resources;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CategoryStockResponse {

    private String categoryName;
    private Long totalStock;
}
