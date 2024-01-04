package com.prodemy.miniprojectbackend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodemy.miniprojectbackend.model.Category;
import com.prodemy.miniprojectbackend.repository.CategoryRepository;
import com.prodemy.miniprojectbackend.service.CategoryService;

@Service
public class CategoryServiceImp implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Category findById(Long id) {
		// TODO Auto-generated method stub
		return categoryRepository.getReferenceById(id);
	}

	
}
