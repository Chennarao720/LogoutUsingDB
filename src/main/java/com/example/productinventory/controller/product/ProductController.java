package com.example.productinventory.controller.product;

import com.example.productinventory.dto.product.ProductResponse;
import com.example.productinventory.dto.product.ProductSaveRequest;
import com.example.productinventory.dto.product.ProductSaveResponse;
import com.example.productinventory.entity.StockUpdateRequest;
import com.example.productinventory.service.product.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping("/addProduct")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ProductSaveResponse> addProduct(
            @RequestBody ProductSaveRequest request) {

        log.info("Add product request received");

        ProductSaveResponse addproduct = service.addproduct(request);

        log.info("Product added successfully");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(addproduct);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {

        log.info("Request received to get all products");

        List<ProductResponse> products = service.getAllProducts();

        log.info("Successfully retrieved {} products", products.size());

        return ResponseEntity.ok(products);
    }

    @GetMapping("/getProduct/{id}")
    public ResponseEntity<ProductResponse> getProductById(
            @PathVariable Long id) {

        log.info("Request received to get product with id: {}", id);

        ProductResponse response = service.getProductById(id);

        log.info("Product retrieved successfully with id: {}", id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/stock")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ProductSaveResponse> updateStock(
            @PathVariable Long id,
            @Valid @RequestBody StockUpdateRequest request) {

        log.info("Stock update request received for product id: {}", id);

        ProductSaveResponse response =
                service.updateStock(id, request);

        log.info("Stock updated successfully for product id: {}", id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/sortByDesc/price")
    public ResponseEntity<List<ProductResponse>> sortByPrice() {

        log.info("Request received to sort products by price in descending order");

        List<ProductResponse> products = service.sortDesc();

        log.info("Products sorted by price in descending order successfully");

        return ResponseEntity.ok(products);
    }

    @GetMapping("/sortByASC/price")
    public ResponseEntity<List<ProductResponse>> sortByPriceASC() {

        log.info("Request received to sort products by price in ascending order");

        List<ProductResponse> products = service.sortAsc();

        log.info("Products sorted by price in ascending order successfully");

        return ResponseEntity.ok(products);
    }
}