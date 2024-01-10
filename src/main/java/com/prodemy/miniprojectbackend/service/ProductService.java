package com.prodemy.miniprojectbackend.service;

import com.prodemy.miniprojectbackend.model.Product;
import com.prodemy.miniprojectbackend.model.response.ProductResponse;

import java.util.List;

public interface ProductService {

    List<ProductResponse> getAllProduct();

    List<ProductResponse> getProductsWithFilters(Long categoryId, String query, String sortBy);

    ProductResponse saveProduct(Product product);

    Product findByTitle(String title);
    
    Product findById(Long Id);

    void deleteProductById(Long id);
}
