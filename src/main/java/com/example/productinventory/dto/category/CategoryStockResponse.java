package com.example.productinventory.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryStockResponse {
    private String categoryName;
    private Long totalStock;

}
