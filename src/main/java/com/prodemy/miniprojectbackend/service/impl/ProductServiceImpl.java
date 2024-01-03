package com.prodemy.miniprojectbackend.service.impl;

import com.prodemy.miniprojectbackend.model.Product;
import com.prodemy.miniprojectbackend.model.response.ProductResponse;
import com.prodemy.miniprojectbackend.repository.CategoryRepository;
import com.prodemy.miniprojectbackend.repository.ProductRepository;
import com.prodemy.miniprojectbackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<ProductResponse> getAllProduct() {
        return productRepository.findAll().stream().map(
                this::convertProductToProductResponse
        ).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> searchProductByQuery(String query) {
        return productRepository.findAllByTitleContainsIgnoreCase(query).stream().map(
                this::convertProductToProductResponse
        ).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> filterProductByCategory(Long categoryId) {
        return productRepository.findAllByCategoryId(categoryId).stream().map(
                this::convertProductToProductResponse
        ).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> filterProductByCategoryAndQuery(Long categoryId, String query) {
        return productRepository.findAllByCategoryIdAndTitleContainsIgnoreCase(categoryId, query).stream().map(
                this::convertProductToProductResponse
        ).collect(Collectors.toList());
    }

    @Override
    public String addProduct(Product product) {
        productRepository.save(product);
        return "sukses";
    }

    @Override
    public Product findByTitle(String title) {
        return productRepository.findByTitle(title);
    }

    @Override
    public String updateProduct(Product product) {
        return null;
    }

    private ProductResponse convertProductToProductResponse(Product product) {
        String category = categoryRepository.findById(product.getCategoryId()).orElseThrow(RuntimeException::new).getName();

        ProductResponse productResponse = new ProductResponse();

        productResponse.setId(product.getId());
        productResponse.setTitle(product.getTitle());
        productResponse.setImage(product.getImage());
        productResponse.setPrice(product.getPrice());
        productResponse.setCategory(category);

        return productResponse;
    }
}
