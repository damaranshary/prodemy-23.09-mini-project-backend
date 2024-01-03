package com.prodemy.miniprojectbackend.controller;

import com.prodemy.miniprojectbackend.model.WebResponse;
import com.prodemy.miniprojectbackend.model.response.ProductResponse;
import com.prodemy.miniprojectbackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
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

}
