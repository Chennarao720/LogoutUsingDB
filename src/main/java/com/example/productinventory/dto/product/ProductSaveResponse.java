package com.example.productinventory.dto.product;

import com.example.productinventory.dto.category.CategoryResponse;
import com.example.productinventory.entity.AvailabilityOfProduct;
import lombok.Data;

@Data
public class ProductSaveResponse {

    private Long id;

    private String name;

    private String brand;

    private Double price;

    private Integer stock;

    private AvailabilityOfProduct availability;

    private CategoryResponse category;
}
