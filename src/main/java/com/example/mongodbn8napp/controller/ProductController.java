package com.example.mongodbn8napp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.mongodbn8napp.dto.ProductResponseDTO;
import com.example.mongodbn8napp.face.product.ProductFacade;
import com.example.mongodbn8napp.global.ApiResponse;
import com.example.mongodbn8napp.model.Product;
import com.example.mongodbn8napp.service.product.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductFacade productFacade;

    // --- 1. Thêm sản phẩm mới (Create) ---
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDTO>> createProduct(@RequestBody Product product) {
        ApiResponse<ProductResponseDTO> response = productFacade.createProduct(product);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // --- 2. Lấy tất cả sản phẩm (Read All) với phân trang ---
    @GetMapping
    public ResponseEntity<ApiResponse> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        ApiResponse response = productFacade.getAllProducts(pageable);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // --- 3. Lấy sản phẩm theo ID (Read One) ---
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> getProductById(@PathVariable("id") String id) {
        ApiResponse<ProductResponseDTO> response = productFacade.getProductById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // --- 4. Cập nhật sản phẩm (Update) ---
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> updateProduct(@PathVariable("id") String id, @RequestBody Product product) {
        ApiResponse<ProductResponseDTO> response = productFacade.updateProduct(id, product);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // --- 5. Xóa sản phẩm (Delete) ---
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable("id") String id) {
        ApiResponse<Void> response = productFacade.deleteProduct(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // --- 6. Xóa tất cả sản phẩm (Delete All) ---
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteAllProducts() {
        ApiResponse<Void> response = productFacade.deleteAllProducts();
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}