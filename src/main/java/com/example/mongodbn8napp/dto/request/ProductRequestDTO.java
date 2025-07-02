package com.example.mongodbn8napp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {
    @NotBlank(message = "SKU không được để trống")
    private String sku;

    @NotNull(message = "Trạng thái hiển thị không được để trống")
    private Boolean visible;

    private String categoryId; // Bỏ @NotBlank

    @NotNull(message = "Giá sản phẩm không được để trống")
    private Double price;

    private Double discountPrice; // Không bắt buộc

    @NotNull(message = "Thông tin mô tả không được để trống")
    private ProductDescriptionDTO descriptionInfo;

    @NotNull(message = "Thông tin tồn kho không được để trống")
    private ProductAvailabilityDTO availability;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductDescriptionDTO {
        @NotBlank(message = "Tên sản phẩm không được để trống")
        private String name;
        private String description; // Không bắt buộc
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductAvailabilityDTO {
        @NotNull(message = "Số lượng không được để trống")
        private Integer quantity;
    }
}