package com.example.demo.service.impl;

import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.CategoryResponse;
import com.example.demo.entity.Category;
import com.example.demo.exception.ResourceNotFountException;
import com.example.demo.repo.CategoryRepo;
import com.example.demo.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {
//        Category category = new Category();
//        category.setName(categoryDto.getName());
//        category.setDescription(categoryDto.getDescription());
//        category.setIsActive(categoryDto.getIsActive());

        Category category = mapper.map(categoryDto, Category.class);

        if (ObjectUtils.isEmpty(category.getId())) {
            category.setIsDeleted(false);
            category.setCreatedBy(1);
            category.setCreatedOn(new Date());
        }else {
            updateCategory(category);
        }


        Category saveCategory = categoryRepo.save(category);
        if (ObjectUtils.isEmpty(saveCategory)) {
            return false;
        }

        return true;
    }

    private void updateCategory(Category category) {
       Optional<Category> findById = categoryRepo.findById(category.getId());
       if (findById.isPresent()){
           Category existingCategory = findById.get();
           category.setCreatedBy(existingCategory.getCreatedBy());
           category.setCreatedOn(existingCategory.getCreatedOn());
           category.setIsDeleted(existingCategory.getIsDeleted());
           category.setUpdatedBy(1);
           category.setUpdatedOn(new Date());
       }
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryRepo.findByIsDeletedFalse();
        List<CategoryDto> categoryDtoList = categories.stream().map(cat -> mapper.map(cat, CategoryDto.class)).toList();
        return categoryDtoList;
    }

    @Override
    public List<CategoryResponse> getActiveCategory() {
        List<Category> categories = categoryRepo.findByIsActiveTrueAndIsDeletedFalse();
        List<CategoryResponse> categoryList = categories.stream().map(cat -> mapper.map(cat, CategoryResponse.class)).toList();
        return categoryList;
    }

    @Override
    public CategoryDto getCategoryById(Integer id) throws Exception {
        Category category = categoryRepo.findByIdAndIsDeletedFalse(id)
                    .orElseThrow(() -> new ResourceNotFountException("category not found with id = "+id));

        if (!ObjectUtils.isEmpty(category)) {
            return mapper.map(category, CategoryDto.class);
        }
        return null;
    }

    @Override
    public Boolean deleteCategoryById(Integer id) {
        Optional<Category> findByCategory = categoryRepo.findById(id);

        if (findByCategory.isPresent()) {
            Category category = findByCategory.get();
            category.setIsDeleted(true);
            categoryRepo.save(category);
            return true;
        }
        return false;
    }
}
