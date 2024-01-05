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
    public Product findByTitle(String title) {
        return productRepository.findByTitle(title);
    }

    @Override
    public ProductResponse saveProduct(Product product) {
        return convertProductToProductResponse(productRepository.save(product));
    }

    @Override
    public Product findById(Long Id) {
        return productRepository.getReferenceById(Id);
    }

    private ProductResponse convertProductToProductResponse(Product product) {
        String category = categoryRepository.findById(product.getCategoryId()).orElseThrow(RuntimeException::new).getName();

        return ProductResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .image(product.getImage()).price(product.getPrice())
                .category(category).build();
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
