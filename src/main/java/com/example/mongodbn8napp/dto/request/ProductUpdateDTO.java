package com.example.mongodbn8napp.dto.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateDTO {
    private String sku;
    private Boolean visible;
    private String categoryId;
    private Double price; // Thêm trường giá
    private Double discountPrice; // Thêm trường giá sau giảm
    private ProductDescriptionDTO descriptionInfo;
    private ProductAvailabilityDTO availability;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductDescriptionDTO {
        private String name;
        private String description;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductAvailabilityDTO {
        private Integer quantity;
    }
}