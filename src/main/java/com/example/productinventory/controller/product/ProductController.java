package com.example.productinventory.controller.product;

import com.example.productinventory.dto.product.ProductResponse;
import com.example.productinventory.dto.product.ProductSaveRequest;
import com.example.productinventory.dto.product.ProductSaveResponse;
import com.example.productinventory.entity.StockUpdateRequest;
import com.example.productinventory.service.product.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping("/addProduct")
    public ResponseEntity<ProductSaveResponse> addProduct(@RequestBody ProductSaveRequest request){
        ProductSaveResponse addproduct = service.addproduct(request);
        return ResponseEntity.status(HttpStatus.OK).body(addproduct);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = service.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/getProduct/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        ProductResponse response = service.getProductById(id);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}/stock")
    public ResponseEntity<ProductSaveResponse> updateStock(
            @PathVariable Long id,
            @Valid @RequestBody StockUpdateRequest request) {
        ProductSaveResponse response =
                service.updateStock(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/sortByDesc/price")
    public ResponseEntity<List<ProductResponse>> sortByPrice() {
        return ResponseEntity.ok(
                service.sortDesc()
        );
    }

    @GetMapping("/sortByASC/price")
    public ResponseEntity<List<ProductResponse>> sortByPriceASC() {
        return ResponseEntity.ok(service.sortAsc());
    }
}
