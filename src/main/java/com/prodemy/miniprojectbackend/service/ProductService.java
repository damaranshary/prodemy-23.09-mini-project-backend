package com.prodemy.miniprojectbackend.service;

import com.prodemy.miniprojectbackend.model.Product;
import com.prodemy.miniprojectbackend.model.response.ProductResponse;

import java.util.List;

public interface ProductService {

    List<ProductResponse> getAllProduct();

    List<ProductResponse> searchProductByQuery(String query);

    List<ProductResponse> filterProductByCategory(Long categoryId);

    List<ProductResponse> filterProductByCategoryAndQuery(Long categoryId, String query);

    void saveProduct(Product product);

    Product findByTitle(String title);
    
    Product findById(Long Id);
}
