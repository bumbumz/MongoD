package com.example.mongodbn8napp.face.product;


import com.example.mongodbn8napp.dto.ProductResponseDTO;
import com.example.mongodbn8napp.global.ApiResponse;
import com.example.mongodbn8napp.model.Product;
import org.springframework.data.domain.Pageable;

public interface ProductFacade {
    ApiResponse<ProductResponseDTO> createProduct(Product product);
    ApiResponse getAllProducts(Pageable pageable);
    ApiResponse<ProductResponseDTO> getProductById(String id);
    ApiResponse<ProductResponseDTO> updateProduct(String id, Product product);
    ApiResponse<Void> deleteProduct(String id);
    ApiResponse<Void> deleteAllProducts();
}
