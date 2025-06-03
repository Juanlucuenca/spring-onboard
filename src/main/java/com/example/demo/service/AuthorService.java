package com.example.demo.service;

import com.example.demo.dto.author.AuthorCreateDto;
import com.example.demo.dto.author.AuthorResponseDto;
import com.example.demo.mapper.AuthorMapper;
import com.example.demo.persistance.model.AuthorEntity;
import com.example.demo.persistance.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorService {
    
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    
    @Transactional(readOnly = true)
    public List<AuthorResponseDto> findAll() {
        List<AuthorEntity> authors = authorRepository.findAll();
        return authorMapper.toResponseDtoList(authors);
    }
    
    public AuthorResponseDto create(AuthorCreateDto createDto) {

        if (StringUtils.hasText(createDto.getEmail()) && authorRepository.existsByEmail(createDto.getEmail())) {
            throw new DataIntegrityViolationException("El mail ya existe: " + createDto.getEmail());
        }
        
        AuthorEntity entity = authorMapper.toEntity(createDto);
        AuthorEntity savedEntity = authorRepository.save(entity);
        
        return authorMapper.toResponseDto(savedEntity);
    }
    
    @Transactional(readOnly = true)
    public Optional<AuthorResponseDto> findById(Long id) {

        Optional<AuthorEntity> author = authorRepository.findById(id);
        if (author.isPresent()) {
            return Optional.of(authorMapper.toResponseDto(author.get()));
        } else {
            return Optional.empty();
        }
    }

    public Optional<AuthorResponseDto> update(Long id, AuthorCreateDto updateDto) {

        Optional<AuthorEntity> existingAuthor = authorRepository.findById(id);
        if (existingAuthor.isEmpty()) {
            return Optional.empty();
        }
        
        if (StringUtils.hasText(updateDto.getEmail()) && 
            authorRepository.existsByEmailAndIdNot(updateDto.getEmail(), id)) {
            throw new DataIntegrityViolationException("El email ya existe: " + updateDto.getEmail());
        }
        
        AuthorEntity entity = existingAuthor.get();
        authorMapper.updateEntityFromDto(entity, updateDto);
        AuthorEntity savedEntity = authorRepository.save(entity);
        
        return Optional.of(authorMapper.toResponseDto(savedEntity));
    }
    
    public boolean delete(Long id) {

        if (!authorRepository.existsById(id)) {
            return false;
        }
        
        authorRepository.deleteById(id);
        return true;
    }
    
    @Transactional(readOnly = true)
    public List<AuthorResponseDto> searchByName(String name) {
        
        if (!StringUtils.hasText(name)) {
            return List.of();
        }
        
        List<AuthorEntity> authors = authorRepository.findByNameContainingIgnoreCase(name);
        return authorMapper.toResponseDtoList(authors);
    }
    
    @Transactional(readOnly = true)
    public Optional<AuthorResponseDto> findByEmail(String email) {
        
        if (!StringUtils.hasText(email)) {
            return Optional.empty();
        }
        
        Optional<AuthorEntity> author = authorRepository.findByEmail(email);
        if (author.isPresent()) {
            return Optional.of(authorMapper.toResponseDto(author.get()));
        } else {
            return Optional.empty();
        }
    }
    
    @Transactional(readOnly = true)
    public List<AuthorResponseDto> findAllOrderedByName() {
        List<AuthorEntity> authors = authorRepository.findAllOrderedByName();
        return authorMapper.toResponseDtoList(authors);
    }
    
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return authorRepository.existsById(id);
    }
}
