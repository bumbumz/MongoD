package com.example.mongodbn8napp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field; // Import này nếu muốn đổi tên trường trong MongoDB

import java.time.LocalDateTime; // Sử dụng LocalDateTime cho các trường ngày giờ
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products") // Collection trong MongoDB sẽ là "products"
public class Product {

    @Id // Trường này sẽ ánh xạ tới _id trong MongoDB
    private String id;

    private String sku;
    private Boolean visible; // 'boolean' cho 'visible'
    private LocalDateTime dateAdd; // Sử dụng LocalDateTime cho ngày tạo
    private LocalDateTime dateUpdate; // Sử dụng LocalDateTime cho ngày cập nhật
    // Thêm hai trường mới
    private String categoryId; // ID của danh mục n
    private String categoryName; // Tên của danh mục để lưu trữ trực tiếp
    // Nhúng ProductDescription vào Product
    private ProductDescription descriptionInfo; // Tên trường trong Java là descriptionInfo

    private Double price; // Thêm trường giá gốc
    private Double discountPrice; // Thêm trường giá sau giảm, có thể null
    
    // Nhúng ProductAvailability vào Product
    private ProductAvailability availability; // Tên trường trong Java là availability

    private List<ProductImage> images;
    private Boolean deleted = false; // Mặc định là false (chưa xóa)
}