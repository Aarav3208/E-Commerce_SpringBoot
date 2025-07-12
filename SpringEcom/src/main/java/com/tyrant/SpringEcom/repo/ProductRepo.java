package com.tyrant.SpringEcom.repo;

import com.tyrant.SpringEcom.model.product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<product, Integer> {

    @Query("SELECT p FROM product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<product> searchProducts(String keyword);

    @Query("SELECT p FROM product p WHERE p.productAvailable = true")
    List<product> findAllAvailableProducts();

    @Query("SELECT p FROM product p WHERE p.price < :price")
    List<product> findByPriceLessThan(double price);

    @Query("SELECT p FROM product p WHERE p.stockQuantity < :quantity")
    List<product> findLowStockProducts(int quantity);
}
