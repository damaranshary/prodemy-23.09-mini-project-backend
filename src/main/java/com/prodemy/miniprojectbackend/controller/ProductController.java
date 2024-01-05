package com.prodemy.miniprojectbackend.controller;

import com.prodemy.miniprojectbackend.model.Product;
import com.prodemy.miniprojectbackend.model.WebResponse;
import com.prodemy.miniprojectbackend.model.response.ProductResponse;
import com.prodemy.miniprojectbackend.service.ProductService;
import com.prodemy.miniprojectbackend.service.UploadFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UploadFile uploadFile;

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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<ProductResponse> saveProduct(@RequestParam("title") String title,
                                                    @RequestPart("image") MultipartFile image,
                                                    @RequestParam("price") Long price,
                                                    @RequestParam("categoryId") Long categoryId) throws IOException {

        WebResponse<ProductResponse> result = new WebResponse<>();

        Product product = productService.findByTitle(title);

        if (product == null) {
            String imageURL = uploadFile.uploadFile(image);

            ProductResponse response = productService.saveProduct(Product.builder()
                    .title(title)
                    .image(imageURL)
                    .price(price)
                    .categoryId(categoryId)
                    .build());

            result.setStatus(0);
            result.setMessage("Product berhasil ditambahkan");
            result.setData(response);
        }

        return result;
    }

    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<ProductResponse> updateProduct(
            @RequestParam("title") String title,
            @RequestParam("price") Long price,
            @RequestParam("categoryId") Long categoryId,
            @RequestPart(required = false, name = "image") MultipartFile image,
            @PathVariable Long id) throws IOException {

        WebResponse<ProductResponse> result = new WebResponse<>();

        Product product = productService.findById(id);

        if (product != null) {
            String requestImageURL = null;

            if (image != null) {
                requestImageURL = uploadFile.uploadFile(image);
            }

            ProductResponse response = productService.saveProduct(Product.builder()
                    .id(id)
                    .title(title != null ? title : product.getTitle())
                    .image(requestImageURL != null ? requestImageURL : product.getImage())
                    .price(price != null ? price : product.getPrice())
                    .categoryId(categoryId != null ? categoryId : product.getCategoryId())
                    .build());

            result.setStatus(0);
            result.setMessage("Product berhasil diupdate");
            result.setData(response);
        }

        return result;
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> deleteProduct(@PathVariable Long id) {
    WebResponse<String> response = new WebResponse<>();

    Product product = productService.findById(id);

    if (product != null) {
        productService.deleteProductById(id);
        response.setStatus(0);
        response.setMessage("Product berhasil dihapus");
        response.setData("Product with ID " + id + " deleted successfully");
    } else {
        response.setStatus(404); // Not Found status code
        response.setMessage("Product not found");
        response.setData(null);
    }

    return response;
}

}
