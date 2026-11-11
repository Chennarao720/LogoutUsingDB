package com.example.productinventory.service.product;

import com.example.productinventory.dto.exception.CategoryNotFoundException;
import com.example.productinventory.dto.exception.InsufficientStockException;
import com.example.productinventory.dto.exception.ProductNotFoundException;
import com.example.productinventory.dto.product.ProductResponse;
import com.example.productinventory.dto.product.ProductSaveRequest;
import com.example.productinventory.dto.product.ProductSaveResponse;
import com.example.productinventory.entity.*;
import com.example.productinventory.repository.CategoryRepository;
import com.example.productinventory.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;


@Service
public class ProductService {
    private final ProductRepository repository;
    private final ModelMapper mapper;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository repository, ModelMapper mapper, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }

    public ProductSaveResponse addproduct(ProductSaveRequest request){
        Category category = categoryRepository.findByName(request.getCategoryname()).orElseThrow(() -> {
            return new CategoryNotFoundException("Category is not found with name " + request.getCategoryname());
        });

        Product product = mapper.map(request, Product.class);
        product.setCategory(category);
        product.setAvailability(calculateAvailability(request.getStock()));
        Product save = repository.save(product);
        ProductSaveResponse productResponse = mapper.map(save, ProductSaveResponse.class);
        return productResponse;
    }



    //Common for Updating
    private AvailabilityOfProduct calculateAvailability(Integer stock) {

        if (stock > 10) {
            return AvailabilityOfProduct.IN_STOCK;
        } else if (stock > 0) {
            return AvailabilityOfProduct.LOW_STOCK;
        } else {
            return AvailabilityOfProduct.NO_STOCK;
        }
    }

    public ProductSaveResponse updateStock(
            Long productId,
            StockUpdateRequest request) {

        Product product = repository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product not found with id " + productId
                        ));

        int currentStock = product.getStock();

        if (request.getOperation() == StockOperation.INCREASE) {

            product.setStock(
                    currentStock + request.getQuantity()
            );

        } else if (request.getOperation() == StockOperation.DECREASE) {

            if (currentStock < request.getQuantity()) {
                throw new InsufficientStockException(
                        "Insufficient stock"
                );
            }

            product.setStock(
                    currentStock - request.getQuantity()
            );
        }

        // Recalculate availability AFTER changing stock
        product.setAvailability(
                calculateAvailability(product.getStock())
        );

        Product saved = repository.save(product);

        return mapper.map(saved, ProductSaveResponse.class);
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = repository.findAll();
        return products.stream()
                .map(product -> mapper.map(product, ProductResponse.class))
                .toList();
    }


    public ProductResponse getProductById(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with id: " + id)
                );
        return mapper.map(product, ProductResponse.class);
    }

    //Sort

    public List<ProductResponse> sortDesc(){
        List<ProductResponse> allProducts = getAllProducts();
        List<ProductResponse> list = allProducts.stream().sorted(Comparator.comparingDouble(ProductResponse::getPrice)).toList();
        return list;
    }

    public List<ProductResponse> sortAsc(){
        List<ProductResponse> allProducts = getAllProducts();
        List<ProductResponse> list = allProducts.stream().sorted(Comparator.comparingDouble(ProductResponse::getPrice).reversed()).toList();
        return list;
    }





}
