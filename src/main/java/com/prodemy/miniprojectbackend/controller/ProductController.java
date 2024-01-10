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
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UploadFile uploadFile;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<ProductResponse>> getAllProducts(@RequestParam(required = false, name = "q") String query,
                                                             @RequestParam(required = false, name = "category") String categoryId,
                                                             @RequestParam(required = false, name = "sortBy") String sortBy) {
        List<ProductResponse> productResponseList;

        // if all the parameters are null, we just fetch all products
        if (query == null && categoryId == null && sortBy == null) {
            productResponseList = productService.getAllProduct();
        } else {
            // check if category numbers (Long) or not
            if (categoryId != null && !categoryId.matches("\\d+")) {
                return WebResponse.<List<ProductResponse>>builder().message("Kategori tidak ditemukan").status(102).data(null).build();
            }

            // there is a ternary operator because we need to change the categoryId into Long value
            productResponseList = productService.getProductsWithFilters(categoryId == null ? null : Long.parseLong(categoryId), query, sortBy);
        }

        return WebResponse.<List<ProductResponse>>builder().message("Sukses").status(0).data(productResponseList).build();
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
            result.setMessage("Produk berhasil ditambahkan");
            result.setData(response);
        } else {
            result.setStatus(102);
            result.setMessage("Produk dengan nama yang sama sudah terdaftar");
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
            result.setMessage("Produk berhasil diupdate");
            result.setData(response);
        } else {
            result.setStatus(102);
            result.setMessage("Produk dengan ID " + id + " tidak ditemukan");
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
            response.setMessage("Produk berhasil dihapus");
            response.setData("Produk dengan ID " + id + " berhasil dihapus");
        } else {
            response.setStatus(404); // Not Found status code
            response.setMessage("Produk tidak ditemukan");
            response.setData(null);
        }

        return response;
    }

}
