package com.example.mongodbn8napp.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Không cần @Document hay @Id
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAvailability {

    private int quality; // Tên trường này ánh xạ đến "quality" trong availability object
    private LocalDateTime dateAdd;
    private LocalDateTime dateUpdate;
}