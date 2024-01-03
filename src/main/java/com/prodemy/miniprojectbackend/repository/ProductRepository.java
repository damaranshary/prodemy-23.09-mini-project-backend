package com.prodemy.miniprojectbackend.repository;

import com.prodemy.miniprojectbackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByTitleContainsIgnoreCase(String title);

    List<Product> findAllByCategoryId(Long categoryId);

    List<Product> findAllByCategoryIdAndTitleContainsIgnoreCase(Long categoryId, String title);

    Product findByTitle(String title);
}
