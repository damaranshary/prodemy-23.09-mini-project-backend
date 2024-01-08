package com.prodemy.miniprojectbackend.controller;

import com.prodemy.miniprojectbackend.model.Category;
import com.prodemy.miniprojectbackend.model.WebResponse;
import com.prodemy.miniprojectbackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<Category>> getCategories() {
        List<Category> categories = categoryService.getCategories();

        return WebResponse.<List<Category>>builder().message("Success").status(200).data(categories).build();
    }
}
