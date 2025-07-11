package com.example.mongodbn8napp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    private String id;
    private String sku;
    private boolean visible;
    private String categoryId; // Thêm trường categoryId
    private String categoryName; // Thêm trường categoryName
    private LocalDateTime dateAdd;
    private LocalDateTime dateUpdate;
    private Boolean deleted;
    private Double price; // Thêm trường giá
    private Double discountPrice; // Giá sau giảm, không bắt buộc
    private ProductDescriptionDTO descriptionInfo;
    private ProductAvailabilityDTO availability;
    private List<ProductImageDTO> images;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductDescriptionDTO {
        private String name;
        private String description;
        private LocalDateTime dateAdd;
        private LocalDateTime dateUpdate;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductAvailabilityDTO {
        private int quantity; 
        private LocalDateTime dateAdd;
        private LocalDateTime dateUpdate;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductImageDTO {
        private String url;
        private String altText;
        private int order;
        private boolean isThumbnail;
    }
}