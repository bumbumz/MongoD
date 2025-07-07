package com.example.mongodbn8napp.face.product;


import com.example.mongodbn8napp.dto.ProductResponseDTO;
import com.example.mongodbn8napp.dto.request.ProductRequestDTO;
import com.example.mongodbn8napp.dto.request.ProductUpdateDTO;
import com.example.mongodbn8napp.global.ApiResponse;
import com.example.mongodbn8napp.model.Product;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ProductFacade {
    ApiResponse<ProductResponseDTO> createProduct(ProductRequestDTO productRequest, List<MultipartFile> files);
    ApiResponse getAllProducts(Pageable pageable);
    ApiResponse<ProductResponseDTO> getProductById(String id);
    ApiResponse<ProductResponseDTO> updateProduct(String id, ProductUpdateDTO productRequest);
    ApiResponse<Void> deleteProduct(String id);
    ApiResponse<Void> deleteAllProducts();
    ApiResponse getDeletedProducts(Pageable pageable);
}
