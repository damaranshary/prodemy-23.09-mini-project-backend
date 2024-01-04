package com.prodemy.miniprojectbackend.controller;

import com.prodemy.miniprojectbackend.model.Category;
import com.prodemy.miniprojectbackend.model.Product;
import com.prodemy.miniprojectbackend.model.request.ProductRequest;
import com.prodemy.miniprojectbackend.model.WebResponse;
import com.prodemy.miniprojectbackend.model.response.ProductResponse;
import com.prodemy.miniprojectbackend.repository.CategoryRepository;
import com.prodemy.miniprojectbackend.service.CategoryService;
import com.prodemy.miniprojectbackend.service.ProductService;
import com.prodemy.miniprojectbackend.service.UploadFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;
    
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
													@RequestParam("image") MultipartFile image, 
													@RequestParam("price") Long price,
													@RequestParam("categoryId") Long categoryId) throws IOException {

        WebResponse<ProductResponse> result = new WebResponse<>();

        Product product = productService.findByTitle(title);
        
        String imageURL = uploadFile.uploadFile(image);

        if (product == null) {
            productService.saveProduct(Product.builder()
                    .title(title)
                    .image(imageURL)
                    .price(price)
                    .categoryId(categoryId)
                    .build());
            
            Product getProduct = productService.findByTitle(title);
            Category category = categoryService.findById(getProduct.getCategoryId());

            ProductResponse response = ProductResponse.builder()
            		.id(getProduct.getId())
                    .title(getProduct.getTitle())
                    .image(getProduct.getImage())
                    .price(getProduct.getPrice())
                    .category(category.getName())
                    .build();

            result.setStatus(0);
            result.setMessage("Product berhasil ditambahkan");
            result.setData(response);
        }

        return result;
    }
    
    @PatchMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<ProductResponse> updateProduct(@RequestPart("title") String title, 
    												  @RequestPart("image") MultipartFile image, 
    												  @RequestPart("price") Long price,
    												  @RequestPart("categoryId") Long categoryId,
    												  @PathVariable Long id) throws IOException{
    	
    	WebResponse<ProductResponse> result = new WebResponse<>();
    	
    	Product product = productService.findById(id);
    	
    	String imageURL = uploadFile.uploadFile(image);
    	
    	if (product != null) {
    		productService.saveProduct(Product.builder()
    				.id(id)
    				.title(title != null ? title : product.getTitle())
    				.image(imageURL != null ? imageURL : product.getImage())
    				.price(price != null ? price : product.getPrice())
    				.categoryId(categoryId != null ? categoryId : product.getCategoryId())
    				.build());
    		
    		Product getProduct = productService.findById(id);
    		Category category = categoryService.findById(getProduct.getCategoryId());
    		
    		
    		ProductResponse response = ProductResponse.builder()
    				.id(getProduct.getId())
    				.title(getProduct.getTitle())
    				.image(getProduct.getImage())
    				.price(getProduct.getPrice())
    				.category(category.getName())
    				.build();
    		
    		result.setStatus(0);
            result.setMessage("Product berhasil diupdate");
            result.setData(response);
    	}
    	
    	return result;
    }
}
