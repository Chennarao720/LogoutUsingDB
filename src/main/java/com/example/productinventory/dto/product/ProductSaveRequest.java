package com.example.productinventory.dto.product;

import com.example.productinventory.dto.category.CategoryRequest;
import com.example.productinventory.entity.AvailabilityOfProduct;
import lombok.Data;

@Data
public class ProductSaveRequest {

    private String name;

    private String brand;

    private Double price;

    private Integer stock;

    private String categoryname;
}
