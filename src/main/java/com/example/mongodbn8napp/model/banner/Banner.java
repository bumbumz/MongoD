package com.example.mongodbn8napp.model.banner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "banners")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Banner {
    @Id
    private String id;
    private String name;
    private String thumbnailUrl;
    private String link;
    private String description;
    private int order;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

// // Cấu trúc một tài liệu (document) mẫu trong collection 'banners'
// {
//   "_id": ObjectId("60a9f2b3a1b2c3d4e5f6a7b8"), // ID duy nhất của banner
//   "name": "Banner khuyến mãi hè", // Tên của banner
//   "thumbnailUrl": "https://example.com/images/banner_summer_promo.jpg", // URL của ảnh thumbnail banner
//   "link": "https://example.com/promo/summer", // Liên kết khi nhấp vào banner (có thể để trống)
//   "description": "Ưu đãi hấp dẫn cho mùa hè sôi động!", // Mô tả ngắn về banner (tùy chọn)
//   "order": 1, // Thứ tự hiển thị của banner (ví dụ: số nhỏ hơn hiển thị trước)
//   "isActive": true, // Trạng thái hiển thị trên frontend (true: hiển thị, false: ẩn)
//   "createdAt": ISODate("2024-06-25T10:00:00Z"), // Ngày tạo banner
//   "updatedAt": ISODate("2024-06-25T10:00:00Z") // Ngày cập nhật gần nhất
// }