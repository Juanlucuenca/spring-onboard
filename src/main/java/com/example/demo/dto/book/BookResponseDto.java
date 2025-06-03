package com.example.demo.dto.book;

import com.example.demo.dto.author.AuthorResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDto {
    
    private Long id;
    
    private String title;
    
    private Long authorId;
    
    private AuthorResponseDto author;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
} 