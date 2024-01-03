package com.prodemy.miniprojectbackend.service;

import com.prodemy.miniprojectbackend.model.Product;

public interface ProductService {
	public String addProduct(Product product);
	public Product findByTitle(String title);
	public String updateProduct(Product product);
}
