package com.example.mongodbn8napp.service.product;


import com.example.mongodbn8napp.global.exception.NotFoundException;
import com.example.mongodbn8napp.global.ApiResponse;
import com.example.mongodbn8napp.global.exception.InvalidException;
import com.example.mongodbn8napp.model.Product;
import com.example.mongodbn8napp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        // Validation
        if (product.getSku() == null || product.getSku().isBlank()) {
            throw new InvalidException("SKU không được để trống");
        }
        if (product.getDescriptionInfo() == null || product.getDescriptionInfo().getName() == null || product.getDescriptionInfo().getName().isBlank()) {
            throw new InvalidException("Tên sản phẩm không được để trống");
        }
        if (product.getAvailability() != null && product.getAvailability().getQuantity() < 0) {
            throw new InvalidException("Số lượng sản phẩm không được âm");
        }

        product.setDateAdd(LocalDateTime.now());
        product.setDateUpdate(LocalDateTime.now());
        if (product.getAvailability() != null) {
            product.getAvailability().setDateAdd(LocalDateTime.now());
        }
        if (product.getDescriptionInfo() != null) {
            product.getDescriptionInfo().setDateAdd(LocalDateTime.now());
        }
        return productRepository.save(product);
    }

    @Override
    public ApiResponse getAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        ApiResponse.Pagination pagination = new ApiResponse.Pagination(
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages()
        );
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Lấy danh sách sản phẩm thành công",
                productPage.getContent(),
                pagination
        );
    }

    @Override
    public Optional<Product> getProductById(String id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new NotFoundException("Không tìm thấy sản phẩm với ID: " + id);
        }
        return product;
    }

    @Override
    public Product updateProduct(String id, Product product) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isEmpty()) {
            throw new NotFoundException("Không tìm thấy sản phẩm với ID: " + id);
        }
        // Validation
        if (product.getSku() == null || product.getSku().isBlank()) {
            throw new InvalidException("SKU không được để trống");
        }
        if (product.getDescriptionInfo() == null || product.getDescriptionInfo().getName() == null || product.getDescriptionInfo().getName().isBlank()) {
            throw new InvalidException("Tên sản phẩm không được để trống");
        }
        if (product.getAvailability() != null && product.getAvailability().getQuantity() < 0) {
            throw new InvalidException("Số lượng sản phẩm không được âm");
        }

        Product updatedProduct = existingProduct.get();
        updatedProduct.setSku(product.getSku());
        updatedProduct.setVisible(product.isVisible());
        updatedProduct.setDescriptionInfo(product.getDescriptionInfo());
        updatedProduct.setAvailability(product.getAvailability());
        updatedProduct.setImages(product.getImages());
        updatedProduct.setDateUpdate(LocalDateTime.now());

        if (updatedProduct.getAvailability() != null) {
            updatedProduct.getAvailability().setDateUpdate(LocalDateTime.now());
        }
        if (updatedProduct.getDescriptionInfo() != null) {
            updatedProduct.getDescriptionInfo().setDateUpdate(LocalDateTime.now());
        }
        return productRepository.save(updatedProduct);
    }

    @Override
    public boolean deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("Không tìm thấy sản phẩm với ID: " + id);
        }
        productRepository.deleteById(id);
        return true;
    }

    @Override
    public void deleteAllProducts() {
        productRepository.deleteAll();
    }
}