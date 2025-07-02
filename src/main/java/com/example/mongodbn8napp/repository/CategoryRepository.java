package com.example.mongodbn8napp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.mongodbn8napp.model.Cate.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {
}