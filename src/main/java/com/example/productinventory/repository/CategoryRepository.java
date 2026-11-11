package com.example.productinventory.repository;

import com.example.productinventory.dto.category.CategoryStockResponse;
import com.example.productinventory.dto.category.LowStockResponse;
import com.example.productinventory.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByName(String name);


    @Query("""
        SELECT new com.example.productinventory.dto.category.CategoryStockResponse(
            c.name,
            COALESCE(SUM(p.stock), 0L)
        )
        FROM Category c
        LEFT JOIN Product p ON p.category.id = c.id
        GROUP BY c.id, c.name
        """)
    List<CategoryStockResponse> getTotalStockByCategory();

    @Query("""
  select new com.example.productinventory.dto.category.LowStockResponse(
  c.id as categoryId,c.name as categoryName,p.id as productId,
  p.name as productName,p.stock as productStock,p.availability)from Category c Inner Join Product p
  on c.id=p.category.id where p.stock<=10
""")
   List<LowStockResponse> lowstockResponse();
}