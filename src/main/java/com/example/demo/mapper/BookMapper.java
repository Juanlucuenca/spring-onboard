package com.example.demo.mapper;

import com.example.demo.dto.book.BookCreateDto;
import com.example.demo.dto.book.BookResponseDto;
import com.example.demo.persistance.model.BookEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookMapper {
    
    private final AuthorMapper authorMapper;
    
    public BookEntity toEntity(BookCreateDto dto) {
        if (dto == null) {
            return null;
        }
        
        BookEntity entity = new BookEntity();
        entity.setTitle(dto.getTitle());
        entity.setAuthorId(dto.getAuthorId());
        return entity;
    }
    
    public BookResponseDto toResponseDto(BookEntity entity) {
        if (entity == null) {
            return null;
        }
        
        BookResponseDto dto = new BookResponseDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setAuthorId(entity.getAuthorId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        
        if (entity.getAuthor() != null) {
            dto.setAuthor(authorMapper.toResponseDto(entity.getAuthor()));
        }
        
        return dto;
    }
    
    public List<BookResponseDto> toResponseDtoList(List<BookEntity> entities) {
        if (entities == null) {
            return null;
        }
        
        return entities.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
    
    public void updateEntityFromDto(BookEntity entity, BookCreateDto dto) {
        if (entity == null || dto == null) {
            return;
        }
        
        entity.setTitle(dto.getTitle());
        entity.setAuthorId(dto.getAuthorId());
    }
} 