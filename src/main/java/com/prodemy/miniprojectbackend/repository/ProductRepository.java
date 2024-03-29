package com.prodemy.miniprojectbackend.repository;

import com.prodemy.miniprojectbackend.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByTitleContainsIgnoreCase(String title);

    List<Product> findAllByTitleContainsIgnoreCase(String title, Sort sort);

    List<Product> findAllByCategoryId(Long categoryId);

    List<Product> findAllByCategoryId(Long categoryId, Sort sort);

    List<Product> findAllByCategoryIdAndTitleContainsIgnoreCase(Long categoryId, String title);

    List<Product> findAllByCategoryIdAndTitleContainsIgnoreCase(Long categoryId, String title, Sort sort);

    Product findByTitle(String title);
}
