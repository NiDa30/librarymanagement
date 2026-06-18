package com.example.library_management.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BookCreateDTO {
    private String title;
    private String author;
    private Integer stock;
    private MultipartFile coverImage;
}
