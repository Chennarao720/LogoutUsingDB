package com.example.productinventory.service.category;

import com.example.productinventory.dto.category.CategoryRequest;
import com.example.productinventory.dto.category.CategoryResponse;
import com.example.productinventory.entity.Category;
import com.example.productinventory.entity.Product;
import com.example.productinventory.entity.Status;
import com.example.productinventory.repository.CategoryRepository;
import com.example.productinventory.repository.ProductRepository;
import com.example.productinventory.resources.CategoryStockResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository repository;
    private final ModelMapper mapper;
    private final ProductRepository repo;


    public CategoryService(CategoryRepository repository, ModelMapper mapper,ProductRepository repo) {
        this.repository = repository;
        this.mapper = mapper;
        this.repo=repo;
    }

    public CategoryResponse addCategory(CategoryRequest request) {

        Category category = mapper.map(request, Category.class);
        category.setStaus(Status.ACTIVE);
        Category savedCategory = repository.save(category);
        return mapper.map(savedCategory, CategoryResponse.class);
    }


    public List<CategoryStockResponse> getTotalStockByCategory() {
        List<Product> products = repo.findAll();

        return products.stream()
                .collect(Collectors.groupingBy(
                        product -> product.getCategory().getName(),
                        Collectors.summingInt(Product::getStock)
                ))
                .entrySet()
                .stream()
                .map(entry -> new CategoryStockResponse(
                        entry.getKey(),
                        entry.getValue().longValue()
                ))
                .toList();
    }
}
