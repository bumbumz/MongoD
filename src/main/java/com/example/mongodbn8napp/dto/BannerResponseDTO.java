package com.example.mongodbn8napp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BannerResponseDTO {
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
