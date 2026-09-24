package com.example.productinventory.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class StockUpdateRequest {
    @Positive
    private Integer quantity;

    @NotNull
    private StockOperation operation;
}
