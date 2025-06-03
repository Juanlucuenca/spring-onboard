package com.example.demo.dto.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorResponseDto {
    
    private Long id;
    
    private String name;
    
    private String email;
    
    private String website;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
} 