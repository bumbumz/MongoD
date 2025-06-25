package com.example.mongodbn8napp.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



@Data
public class BannerRequestDTO {
    @NotBlank(message = "Tên banner không được để trống")
    private String name;

    private String link;

    private String description;

    @Min(value = 0, message = "Thứ tự banner không được âm")
    private int order;

    @NotNull(message = "Trạng thái active không được để trống")
    private Boolean isActive;

    @NotNull(message = "Ảnh thumbnail không được để trống")
    private MultipartFile thumbnail;
}
