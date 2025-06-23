package com.example.mongodbn8napp.service.product;

import java.util.List;
import java.util.Optional;

import com.example.mongodbn8napp.model.Product;

public interface ProductService {

    List<Product> getAllProducts();
    Optional<Product> getProductById(String id);
    Product createProduct(Product product);
    Product updateProduct(String id, Product product);
    boolean deleteProduct(String id); // Trả về true nếu xóa thành công, false nếu không tìm thấy
    void deleteAllProducts();
}
