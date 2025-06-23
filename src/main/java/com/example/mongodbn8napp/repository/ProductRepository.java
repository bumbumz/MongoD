package com.example.mongodbn8napp.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.mongodbn8napp.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    // Các phương thức CRUD cơ bản vẫn hoạt động trên Product document
    // findById, findAll, save, deleteById, v.v.
}
