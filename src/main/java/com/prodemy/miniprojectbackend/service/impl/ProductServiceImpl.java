package com.prodemy.miniprojectbackend.service.impl;

import com.prodemy.miniprojectbackend.model.Product;
import com.prodemy.miniprojectbackend.model.response.ProductResponse;
import com.prodemy.miniprojectbackend.repository.CategoryRepository;
import com.prodemy.miniprojectbackend.repository.ProductRepository;
import com.prodemy.miniprojectbackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public List<ProductResponse> getProductsWithFilters(Long categoryId, String query, String sortBy) {
        List<Product> productResponse;

        // default get Product
        if (query == null && categoryId == null && sortBy == null) {
            return this.getAllProduct();
        }
        // get all but sorted
        else if (categoryId == null && query == null) {
            productResponse = switch (sortBy) {
                case "nameAsc" -> productRepository.findAll(Sort.by(Sort.Direction.ASC, "title"));
                case "nameDesc" -> productRepository.findAll(Sort.by(Sort.Direction.DESC, "title"));
                case "priceAsc" -> productRepository.findAll(Sort.by(Sort.Direction.ASC, "price"));
                case "priceDesc" -> productRepository.findAll(Sort.by(Sort.Direction.DESC, "price"));
                default -> productRepository.findAll();
            };
        }
        // get only by Category
        else if (sortBy == null && query == null) {
            productResponse = productRepository.findAllByCategoryId(categoryId);
        }
        // get only by Query
        else if (sortBy == null && categoryId == null) {
            productResponse = productRepository.findAllByTitleContainsIgnoreCase(query);
        }
        // get by Query and Categories
        else if (sortBy == null) {
            productResponse = productRepository.findAllByCategoryIdAndTitleContainsIgnoreCase(categoryId, query);
        }
        // get by Query and sorted
        else if (categoryId == null) {
            productResponse = switch (sortBy) {
                case "nameAsc" ->
                        productRepository.findAllByTitleContainsIgnoreCase(query, Sort.by(Sort.Direction.ASC, "title"));
                case "nameDesc" ->
                        productRepository.findAllByTitleContainsIgnoreCase(query, Sort.by(Sort.Direction.DESC, "title"));
                case "priceAsc" ->
                        productRepository.findAllByTitleContainsIgnoreCase(query, Sort.by(Sort.Direction.ASC, "price"));
                case "priceDesc" ->
                        productRepository.findAllByTitleContainsIgnoreCase(query, Sort.by(Sort.Direction.DESC, "price"));
                default -> productRepository.findAllByTitleContainsIgnoreCase(query);
            };
        }
        // get by Categories and sorted
        else if (query == null) {
            productResponse = switch (sortBy) {
                case "nameAsc" ->
                        productRepository.findAllByCategoryId(categoryId, Sort.by(Sort.Direction.ASC, "title"));
                case "nameDesc" ->
                        productRepository.findAllByCategoryId(categoryId, Sort.by(Sort.Direction.DESC, "title"));
                case "priceAsc" ->
                        productRepository.findAllByCategoryId(categoryId, Sort.by(Sort.Direction.ASC, "price"));
                case "priceDesc" ->
                        productRepository.findAllByCategoryId(categoryId, Sort.by(Sort.Direction.DESC, "price"));
                default -> productRepository.findAllByCategoryId(categoryId);
            };
        }
        // get with all filters and sorted
        else {
            productResponse = switch (sortBy) {
                case "nameAsc" ->
                        productRepository.findAllByCategoryIdAndTitleContainsIgnoreCase(categoryId, query, Sort.by(Sort.Direction.ASC, "title"));
                case "nameDesc" ->
                        productRepository.findAllByCategoryIdAndTitleContainsIgnoreCase(categoryId, query, Sort.by(Sort.Direction.DESC, "title"));
                case "priceAsc" ->
                        productRepository.findAllByCategoryIdAndTitleContainsIgnoreCase(categoryId, query, Sort.by(Sort.Direction.ASC, "price"));
                case "priceDesc" ->
                        productRepository.findAllByCategoryIdAndTitleContainsIgnoreCase(categoryId, query, Sort.by(Sort.Direction.DESC, "price"));
                default -> productRepository.findAllByCategoryIdAndTitleContainsIgnoreCase(categoryId, query);
            };
        }

        return productResponse.stream().map(
                this::convertProductToProductResponse).collect(Collectors.toList());
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
        String category = categoryRepository.findById(product.getCategoryId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found")).getName();

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
