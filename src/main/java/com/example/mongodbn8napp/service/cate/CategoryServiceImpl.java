package com.example.mongodbn8napp.service.cate;
import com.example.mongodbn8napp.global.exception.InvalidException;
import com.example.mongodbn8napp.global.exception.NotFoundException;
import com.example.mongodbn8napp.model.Cate.Category;
import com.example.mongodbn8napp.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        if (category.getName() == null || category.getName().isBlank()) {
            throw new InvalidException("Tên danh mục không được để trống");
        }
        category.setDateAdd(LocalDateTime.now());
        category.setDateUpdate(LocalDateTime.now());
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> getCategoryById(String id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new NotFoundException("Không tìm thấy danh mục với ID: " + id);
        }
        return category;
    }

    @Override
    public Category updateCategory(String id, Category category) {
        Optional<Category> existingCategoryOpt = categoryRepository.findById(id);
        if (existingCategoryOpt.isEmpty()) {
            throw new NotFoundException("Không tìm thấy danh mục với ID: " + id);
        }
        Category existingCategory = existingCategoryOpt.get();

        if (category.getName() != null) {
            if (category.getName().isBlank()) {
                throw new InvalidException("Tên danh mục không được để trống");
            }
            existingCategory.setName(category.getName());
        }

        if (category.getDescription() != null) {
            existingCategory.setDescription(category.getDescription());
        }

        existingCategory.setDateUpdate(LocalDateTime.now());
        return categoryRepository.save(existingCategory);
    }

    @Override
    public boolean deleteCategory(String id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Không tìm thấy danh mục với ID: " + id);
        }
        categoryRepository.deleteById(id);
        return true;
    }
}