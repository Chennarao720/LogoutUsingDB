package com.example.productinventory.controller.category;

import com.example.productinventory.dto.category.CategoryRequest;
import com.example.productinventory.dto.category.CategoryResponse;
import com.example.productinventory.dto.category.CategoryStockResponse;
import com.example.productinventory.dto.category.LowStockResponse;
import com.example.productinventory.service.category.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping("/addCategory")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<CategoryResponse> addCategory(
            @RequestBody CategoryRequest request) {

        log.info("Add category request received");

        CategoryResponse categoryResponse =
                service.addCategory(request);

        log.info("Category added successfully");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryResponse);
    }

    @GetMapping("/groupCategory")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<CategoryStockResponse>> groupByCategory() {

        log.info("Request received to get total stock by category");

        List<CategoryStockResponse> response =
                service.getTotalStockByCategory();

        log.info("Total stock by category retrieved successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/totalCategires")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<CategoryResponse>> categories(){
       return ResponseEntity.ok(service.findAllCatogories());
    }

    @GetMapping("/categoryStockResponse")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<CategoryStockResponse>> categoryStockResponse(){
        return ResponseEntity.ok(service.categoryStockResponse());
    }

    @GetMapping("/lowStockResponse")
    public ResponseEntity<List<LowStockResponse>> stockResponseResponse(){
        return ResponseEntity.status(200).body(service.lowStockResponse());
    }
}