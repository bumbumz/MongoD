package com.example.mongodbn8napp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.mongodbn8napp.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    // Các phương thức CRUD cơ bản vẫn hoạt động trên Product document
    // findById, findAll, save, deleteById, v.v.
    boolean existsBySku(String sku);

    // Chỉ lấy các sản phẩm chưa bị xóa
    @Query("{ 'deleted': false }")
    Page<Product> findAllNotDeleted(Pageable pageable);

    // Chỉ lấy sản phẩm chưa bị xóa theo ID
    @Query("{ '_id': ?0, 'deleted': false }")
    Optional<Product> findByIdNotDeleted(String id);

    // Lấy tất cả sản phẩm, kể cả đã xóa (nếu cần cho admin)
    @Query("{ }")
    List<Product> findAllIncludingDeleted();

    @Query("{ 'deleted': true }")
    Page<Product> findAllByDeletedTrue(Pageable pageable);
}
