package com.example.mongodbn8napp.model;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Không cần @Document vì nó không phải là một collection riêng biệt
// Không cần @Id vì nó là một phần của document cha
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDescription {

    // Không cần id riêng cho ProductDescription khi nhúng
    private String name;
    private String description;

   
    private LocalDateTime dateAdd;
    private LocalDateTime dateUpdate;
}