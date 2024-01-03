package com.prodemy.miniprojectbackend.controller;

import com.prodemy.miniprojectbackend.model.Product;
import com.prodemy.miniprojectbackend.model.request.ProductRequest;
import com.prodemy.miniprojectbackend.model.WebResponse;
import com.prodemy.miniprojectbackend.model.response.ProductResponse;
import com.prodemy.miniprojectbackend.repository.CategoryRepository;
import com.prodemy.miniprojectbackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<ProductResponse>> getAllProducts(@RequestParam(required = false, name = "q") String query,
                                                             @RequestParam(required = false, name = "category") String categoryId) {
        List<ProductResponse> productResponseList;

        if (query == null && categoryId == null) {
            productResponseList = productService.getAllProduct();
        } else if (categoryId == null) {
            productResponseList = productService.searchProductByQuery(query);
        } else if (query == null) {
            productResponseList = productService.filterProductByCategory(Long.parseLong(categoryId));
        } else {
            productResponseList = productService.filterProductByCategoryAndQuery(Long.parseLong(categoryId), query);
        }

        return WebResponse.<List<ProductResponse>>builder().message("Success").status(200).data(productResponseList).build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<ProductResponse> saveProduct(@RequestBody ProductRequest productRequest) {

        WebResponse<ProductResponse> result = new WebResponse<>();

        Product product = productService.findByTitle(productRequest.getTitle());

        if (product == null) {

            productService.addProduct(Product.builder()
                    .title(productRequest.getTitle())
                    .image(productRequest.getImage())
                    .price(productRequest.getPrice())
                    .categoryId(productRequest.getCategoryId())
                    .build());

            ProductResponse response = ProductResponse.builder()
                    .title(productRequest.getTitle())
                    .image(productRequest.getImage())
                    .price(productRequest.getPrice())
                    .category(null)
                    .build();

            result.setStatus(0);
            result.setMessage("Product berhasil ditambahkan");
            result.setData(response);
        }

        return result;
    }
}
