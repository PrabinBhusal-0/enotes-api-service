package com.example.demo.service;

import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.CategoryResponse;
import com.example.demo.entity.Category;
import com.example.demo.exception.ResourceNotFountException;

import java.util.List;

public interface CategoryService {

    public Boolean  saveCategory(CategoryDto categoryDto);

    public List<CategoryDto> getAllCategory();

    public List<CategoryResponse> getActiveCategory();

    public CategoryDto getCategoryById(Integer id) throws Exception;

    public Boolean deleteCategoryById(Integer id);
}
