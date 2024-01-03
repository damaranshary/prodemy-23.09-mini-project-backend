package com.prodemy.miniprojectbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prodemy.miniprojectbackend.dto.ProductDto;
import com.prodemy.miniprojectbackend.model.Product;
import com.prodemy.miniprojectbackend.response.HttpResponse;
import com.prodemy.miniprojectbackend.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping
	public HttpResponse<ProductDto> saveProduct(@RequestBody ProductDto productDto){
		
		HttpResponse<ProductDto> result = new HttpResponse<ProductDto>();
		
		Product product = productService.findByTitle(productDto.getTitle());
		
		if (product == null) {
			productService.addProduct(Product.builder()
					.title(productDto.getTitle())
					.image(productDto.getImage())
					.price(productDto.getPrice())
					.categoryId(productDto.getCategoryId())
					.build());
			
			ProductDto response = ProductDto.builder()
					.title(productDto.getTitle())
					.image(productDto.getImage())
					.price(productDto.getPrice())
					.categoryId(productDto.getCategoryId())
					.build();
			
			result.setStatus(0);
			result.setMessage("Product berhasil ditambahkan");
			result.setData(response);
		}
		
		return result;
	}
}
