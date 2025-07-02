package com.example.mongodbn8napp.model.Branch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "branches")
public class Branch {
    @Id
    private String id;

    private String city; // Tên thành phố
    private String province; // Tên tỉnh
    private String address; // Địa chỉ cụ thể
    private LocalDateTime dateAdd;
    private LocalDateTime dateUpdate;
}