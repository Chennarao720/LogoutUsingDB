package com.example.productinventory.dto.category;

import com.example.productinventory.entity.AvailabilityOfProduct;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LowStockResponse {
    private Long categoryId;
    private String categoryName;
    private Long productId;
    private String productName;
    private Integer productStock;
    private AvailabilityOfProduct availability;
}
