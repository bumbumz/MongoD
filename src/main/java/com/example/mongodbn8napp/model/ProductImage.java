package com.example.mongodbn8napp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {
    private String url;
    private String altText; // Mô tả ảnh cho SEO hoặc người dùng
    private int order; // Thứ tự hiển thị của ảnh (ví dụ: ảnh chính, ảnh phụ)
    private boolean isThumbnail; // Đánh dấu ảnh này có phải là ảnh thumbnail không
}
