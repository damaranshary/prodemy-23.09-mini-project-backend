package com.prodemy.miniprojectbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodemy.miniprojectbackend.model.Product;
import com.prodemy.miniprojectbackend.repository.ProductRepository;

@Service
public class ProductServiceImp implements ProductService {
	
	@Autowired
	ProductRepository productRepo;

	@Override
	public String addProduct(Product product) {
		// TODO Auto-generated method stub
		productRepo.save(product);
		return "sukses";
	}
	
	@Override
	public Product findByTitle(String title) {
		// TODO Auto-generated method stub
		return productRepo.findByTitle(title);
	}

	@Override
	public String updateProduct(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
