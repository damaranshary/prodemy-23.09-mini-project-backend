package com.prodemy.miniprojectbackend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodemy.miniprojectbackend.model.Category;
import com.prodemy.miniprojectbackend.repository.CategoryRepository;
import com.prodemy.miniprojectbackend.service.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> getCategories() {
		return categoryRepository.findAll();
	}
}
