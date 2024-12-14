package com.example.demo.service.impl;

import com.example.demo.entity.Category;
import com.example.demo.repo.CategoryRepo;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public Boolean saveCategory(Category category) {
        category.setIsDeleted(false);//setting the is deleted valu to false because after saving ony we can add value to delete
        category.setCreatedBy(1);
       Category savedCategory = categoryRepo.save(category);
       if (ObjectUtils.isEmpty(savedCategory)){
           return false;
       }
        return true;
    }

    @Override
    public List<Category> getAllCategory() {
        List<Category> categories = categoryRepo.findAll();
        return categories;
    }
}
