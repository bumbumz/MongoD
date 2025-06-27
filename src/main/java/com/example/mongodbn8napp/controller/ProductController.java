package com.example.mongodbn8napp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.mongodbn8napp.dto.ProductResponseDTO;
import com.example.mongodbn8napp.dto.request.ProductRequestDTO;
import com.example.mongodbn8napp.dto.request.ProductUpdateDTO;
import com.example.mongodbn8napp.face.product.ProductFacade;
import com.example.mongodbn8napp.global.ApiResponse;
import com.example.mongodbn8napp.model.Product;
import com.example.mongodbn8napp.service.product.ProductService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductFacade productFacade;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDTO>> createProduct(
            @Valid @ModelAttribute ProductRequestDTO productRequest,
            @RequestParam(value = "images", required = false) List<MultipartFile> files) {
        logger.debug("Received productRequest: {}", productRequest);
        logger.debug("Received files: {}", files != null ? files.size() : "null");
        if (files != null) {
            for (int i = 0; i < files.size(); i++) {
                logger.debug("File {}: name={}, size={}, contentType={}", 
                    i, files.get(i).getOriginalFilename(), files.get(i).getSize(), files.get(i).getContentType());
            }
        }
        ApiResponse<ProductResponseDTO> response = productFacade.createProduct(productRequest, files);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        ApiResponse response = productFacade.getAllProducts(pageable);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> getProductById(@PathVariable("id") String id) {
        ApiResponse<ProductResponseDTO> response = productFacade.getProductById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> updateProduct(
            @PathVariable("id") String id,
            @Valid @RequestBody ProductUpdateDTO productRequest) {
        ApiResponse<ProductResponseDTO> response = productFacade.updateProduct(id, productRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable("id") String id) {
        ApiResponse<Void> response = productFacade.deleteProduct(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteAllProducts() {
        ApiResponse<Void> response = productFacade.deleteAllProducts();
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}