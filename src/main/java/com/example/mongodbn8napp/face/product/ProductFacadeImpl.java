package com.example.mongodbn8napp.face.product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.mongodbn8napp.dto.ProductResponseDTO;
import com.example.mongodbn8napp.dto.request.ProductRequestDTO;
import com.example.mongodbn8napp.dto.request.ProductUpdateDTO;
import com.example.mongodbn8napp.global.ApiResponse;
import com.example.mongodbn8napp.model.Product;
import com.example.mongodbn8napp.model.ProductAvailability;
import com.example.mongodbn8napp.model.ProductDescription;
import com.example.mongodbn8napp.service.product.ProductService;

@Component
public class ProductFacadeImpl implements ProductFacade {

    @Autowired
    private ProductService productService;

    private ProductResponseDTO convertToDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setSku(product.getSku());
        dto.setVisible(product.getVisible());
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
    public ApiResponse<ProductResponseDTO> createProduct(ProductRequestDTO productRequest, List<MultipartFile> files) {
        Product product = new Product();
        product.setSku(productRequest.getSku());
        product.setVisible(productRequest.getVisible());

        ProductDescription description = new ProductDescription();
        description.setName(productRequest.getDescriptionInfo().getName());
        description.setDescription(productRequest.getDescriptionInfo().getDescription());
        product.setDescriptionInfo(description);

        ProductAvailability availability = new ProductAvailability();
        availability.setQuantity(productRequest.getAvailability().getQuantity());
        product.setAvailability(availability);

        Product savedProduct = productService.createProduct(product, files);
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
        Product product = productService.getProductById(id).orElseThrow();
        ProductResponseDTO dto = convertToDTO(product);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Lấy sản phẩm thành công",
                dto,
                null
        );
    }

    @Override
    public ApiResponse<ProductResponseDTO> updateProduct(String id, ProductUpdateDTO productRequest) {
        Product existingProduct = productService.getProductById(id).orElseThrow();

        Product product = new Product();
        product.setId(id);
        product.setImages(existingProduct.getImages()); // Giữ nguyên images

        // Cập nhật chỉ các trường được cung cấp
        if (productRequest.getSku() != null) {
            product.setSku(productRequest.getSku());
        } else {
            product.setSku(existingProduct.getSku());
        }

        if (productRequest.getVisible() != null) {
            product.setVisible(productRequest.getVisible());
        } else {
            product.setVisible(existingProduct.getVisible());
        }

        ProductDescription description = existingProduct.getDescriptionInfo() != null
                ? existingProduct.getDescriptionInfo()
                : new ProductDescription();
        if (productRequest.getDescriptionInfo() != null) {
            if (productRequest.getDescriptionInfo().getName() != null) {
                description.setName(productRequest.getDescriptionInfo().getName());
            }
            if (productRequest.getDescriptionInfo().getDescription() != null) {
                description.setDescription(productRequest.getDescriptionInfo().getDescription());
            }
        }
        product.setDescriptionInfo(description);

        ProductAvailability availability = existingProduct.getAvailability() != null
                ? existingProduct.getAvailability()
                : new ProductAvailability();
        if (productRequest.getAvailability() != null && productRequest.getAvailability().getQuantity() != null) {
            availability.setQuantity(productRequest.getAvailability().getQuantity());
        }
        product.setAvailability(availability);

        Product updatedProduct = productService.updateProduct(id, product);
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
        productService.deleteProduct(id);
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