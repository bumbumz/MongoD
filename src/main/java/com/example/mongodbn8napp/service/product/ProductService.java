package com.example.mongodbn8napp.service.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.example.mongodbn8napp.global.ApiResponse;
import com.example.mongodbn8napp.model.Product;

public interface ProductService {
    Product createProduct(Product product, List<MultipartFile> files);
    ApiResponse getAllProducts(Pageable pageable);
    Optional<Product> getProductById(String id);
    Product updateProduct(String id, Product product);
    boolean deleteProduct(String id);
    void deleteAllProducts();
}
