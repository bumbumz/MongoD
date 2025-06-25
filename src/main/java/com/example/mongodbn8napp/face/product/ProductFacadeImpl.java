package com.example.mongodbn8napp.face.product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.example.mongodbn8napp.dto.ProductResponseDTO;
import com.example.mongodbn8napp.global.ApiResponse;
import com.example.mongodbn8napp.model.Product;
import com.example.mongodbn8napp.service.product.ProductService;

@Component
public class ProductFacadeImpl implements ProductFacade {

    @Autowired
    private ProductService productService;

    private ProductResponseDTO convertToDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setSku(product.getSku());
        dto.setVisible(product.isVisible());
        dto.setDateAdd(product.getDateAdd());
        dto.setDateUpdate(product.getDateUpdate());

        // Chuyển đổi descriptionInfo
        ProductResponseDTO.ProductDescriptionDTO descriptionDTO = new ProductResponseDTO.ProductDescriptionDTO(
                product.getDescriptionInfo().getName(),
                product.getDescriptionInfo().getDescription(),
                product.getDescriptionInfo().getDateAdd(),
                product.getDescriptionInfo().getDateUpdate()
        );
        dto.setDescriptionInfo(descriptionDTO);

        // Chuyển đổi availability
        ProductResponseDTO.ProductAvailabilityDTO availabilityDTO = new ProductResponseDTO.ProductAvailabilityDTO(
                product.getAvailability().getQuantity(),
                product.getAvailability().getDateAdd(),
                product.getAvailability().getDateUpdate()
        );
        dto.setAvailability(availabilityDTO);

        // Chuyển đổi images
        List<ProductResponseDTO.ProductImageDTO> imageDTOs = product.getImages().stream()
                .map(image -> new ProductResponseDTO.ProductImageDTO(
                        image.getUrl(),
                        image.getAltText(),
                        image.getOrder(),
                        image.isThumbnail()
                ))
                .collect(Collectors.toList());
        dto.setImages(imageDTOs);

        return dto;
    }

    @Override
    public ApiResponse<ProductResponseDTO> createProduct(Product product) {
        Product savedProduct = productService.createProduct(product);
        ProductResponseDTO dto = convertToDTO(savedProduct);
        return new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Tạo sản phẩm thành công",
                dto,
                null
        );
    }

    @Override
    public ApiResponse getAllProducts(Pageable pageable) {
        ApiResponse<List<Product>> serviceResponse = productService.getAllProducts(pageable);
        List<ProductResponseDTO> dtos = serviceResponse.getData().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ApiResponse<>(
                serviceResponse.getStatus(),
                serviceResponse.getMessage(),
                dtos,
                serviceResponse.getPagination()
        );
    }

    @Override
    public ApiResponse<ProductResponseDTO> getProductById(String id) {
        Optional<Product> productOptional = productService.getProductById(id); // Sẽ ném ProductNotFoundException nếu không tìm thấy
        ProductResponseDTO dto = convertToDTO(productOptional.get());
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Lấy sản phẩm thành công",
                dto,
                null
        );
    }

    @Override
    public ApiResponse<ProductResponseDTO> updateProduct(String id, Product product) {
        Product updatedProduct = productService.updateProduct(id, product); // Sẽ ném ProductNotFoundException nếu không tìm thấy
        ProductResponseDTO dto = convertToDTO(updatedProduct);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Cập nhật sản phẩm thành công",
                dto,
                null
        );
    }

    @Override
    public ApiResponse<Void> deleteProduct(String id) {
        productService.deleteProduct(id); // Sẽ ném NotFoundException nếu không tìm thấy
        return new ApiResponse<>(
                HttpStatus.NO_CONTENT.value(),
                "Xóa sản phẩm thành công",
                null,
                null
        );
    }

    @Override
    public ApiResponse<Void> deleteAllProducts() {
        productService.deleteAllProducts();
        return new ApiResponse<>(
                HttpStatus.NO_CONTENT.value(),
                "Xóa tất cả sản phẩm thành công",
                null,
                null
        );
    }
}