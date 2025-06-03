package com.example.demo.mapper;

import com.example.demo.dto.author.AuthorCreateDto;
import com.example.demo.dto.author.AuthorResponseDto;
import com.example.demo.persistance.model.AuthorEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorMapper {
    
    public AuthorEntity toEntity(AuthorCreateDto dto) {
        if (dto == null) {
            return null;
        }
        
        AuthorEntity entity = new AuthorEntity();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setWebsite(dto.getWebsite());
        return entity;
    }
    
    public AuthorResponseDto toResponseDto(AuthorEntity entity) {
        if (entity == null) {
            return null;
        }
        
        AuthorResponseDto dto = new AuthorResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setWebsite(entity.getWebsite());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
    
    public List<AuthorResponseDto> toResponseDtoList(List<AuthorEntity> entities) {
        if (entities == null) {
            return null;
        }
        
        return entities.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
    
    public void updateEntityFromDto(AuthorEntity entity, AuthorCreateDto dto) {
        if (entity == null || dto == null) {
            return;
        }
        
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setWebsite(dto.getWebsite());
    }
} 