package com.example.productinventory.controller.category;

import com.example.productinventory.dto.category.CategoryRequest;
import com.example.productinventory.dto.category.CategoryResponse;
import com.example.productinventory.service.category.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping("/addCategory")
    public ResponseEntity<CategoryResponse> addCategory(@RequestBody CategoryRequest request){
        CategoryResponse categoryResponse = service.addCategory(request);
      return   ResponseEntity.status(HttpStatus.CREATED).body(categoryResponse);
    }
}
