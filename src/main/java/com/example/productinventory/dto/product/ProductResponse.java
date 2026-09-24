package com.example.productinventory.dto.product;

import com.example.productinventory.dto.category.CategoryResponse;
import com.example.productinventory.entity.AvailabilityOfProduct;
import lombok.Data;

@Data
public class ProductResponse {
    private Long id;

    private String name;

    private String brand;

    private Double price;

    private AvailabilityOfProduct availability;

}
