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
    private Double price; // Thêm trường giá

    private Double discountPrice; // Giá sau giảm, không bắt buộc

    @NotNull(message = "Thông tin mô tả không được để trống")
    private ProductDescriptionDTO descriptionInfo;

    @NotNull(message = "Thông tin tồn kho không được để trống")
    private ProductAvailabilityDTO availability;
    @NotBlank(message = "ID danh mục không được để trống")
    private String categoryId; 

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductDescriptionDTO {
        @NotBlank(message = "Tên sản phẩm không được để trống")
        private String name;

        @NotBlank(message = "Mô tả không được để trống")
        private String description;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductAvailabilityDTO {
        @NotNull(message = "Số lượng không được để trống")
        private Integer quantity;
    }
}