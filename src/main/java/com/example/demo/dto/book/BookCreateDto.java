package com.example.demo.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateDto {
    
    @NotBlank(message = "Book title is required")
    @Size(min = 1, max = 200, message = "Book title must be between 1 and 200 characters")
    private String title;
    
    private Long authorId;
} 