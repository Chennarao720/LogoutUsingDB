package com.example.productinventory.dto.category;

import com.example.productinventory.entity.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class CategoryRequest {
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status  status=Status.ACTIVE;
}
