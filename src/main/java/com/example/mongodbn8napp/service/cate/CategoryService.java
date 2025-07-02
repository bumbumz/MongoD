package com.example.mongodbn8napp.service.cate;

import org.springframework.data.domain.Pageable;

import com.example.mongodbn8napp.model.Cate.Category;

import java.util.Optional;

public interface CategoryService {
    Category createCategory(Category category);
    Optional<Category> getCategoryById(String id);
    Category updateCategory(String id, Category category);
    boolean deleteCategory(String id);
}