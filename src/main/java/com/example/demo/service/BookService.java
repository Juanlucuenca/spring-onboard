package com.example.demo.service;

import com.example.demo.dto.book.BookCreateDto;
import com.example.demo.dto.book.BookResponseDto;
import com.example.demo.mapper.BookMapper;
import com.example.demo.persistance.model.BookEntity;
import com.example.demo.persistance.repository.BookRepository;
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
public class BookService {
    
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorService authorService;

    @Transactional(readOnly = true)
    public List<BookResponseDto> findAll() {
        List<BookEntity> books = bookRepository.findAllWithAuthors();
        return bookMapper.toResponseDtoList(books);
    }
    
    public BookResponseDto create(BookCreateDto createDto) {

        if (bookRepository.existsByTitle(createDto.getTitle())) {
            throw new DataIntegrityViolationException("El título ya existe: " + createDto.getTitle());
        }
        
        if (createDto.getAuthorId() != null && !authorService.existsById(createDto.getAuthorId())) {
            throw new DataIntegrityViolationException("El autor con ID: " + createDto.getAuthorId() + " no existe");
        }
        
        BookEntity entity = bookMapper.toEntity(createDto);
        BookEntity savedEntity = bookRepository.save(entity);
        
        return bookMapper.toResponseDto(savedEntity);
    }
    
    @Transactional(readOnly = true)
    public Optional<BookResponseDto> findById(Long id) {
        
        Optional<BookEntity> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return Optional.of(bookMapper.toResponseDto(book.get()));
        } else {
            return Optional.empty();
        }
    }
    
    public Optional<BookResponseDto> update(Long id, BookCreateDto updateDto) {
        
        Optional<BookEntity> existingBook = bookRepository.findById(id);
        if (existingBook.isEmpty()) {
            return Optional.empty();
        }
        
        if (bookRepository.existsByTitleAndIdNot(updateDto.getTitle(), id)) {
            throw new DataIntegrityViolationException("El título ya existe: " + updateDto.getTitle());
        }
        
        if (updateDto.getAuthorId() != null && !authorService.existsById(updateDto.getAuthorId())) {
            throw new DataIntegrityViolationException("El autor con ID: " + updateDto.getAuthorId() + " no existe");
        }
        
        BookEntity entity = existingBook.get();
        bookMapper.updateEntityFromDto(entity, updateDto);
        BookEntity savedEntity = bookRepository.save(entity);
        
        return Optional.of(bookMapper.toResponseDto(savedEntity));
    }
    
    public boolean delete(Long id) {
        
        if (!bookRepository.existsById(id)) {
            return false;
        }
        
        bookRepository.deleteById(id);
        return true;
    }
    
    @Transactional(readOnly = true)
    public Optional<BookResponseDto> findByTitle(String title) {
        
        if (!StringUtils.hasText(title)) {
            return Optional.empty();
        }
        
        Optional<BookEntity> book = bookRepository.findByTitle(title);
        if (book.isPresent()) {
            return Optional.of(bookMapper.toResponseDto(book.get()));
        } else {
            return Optional.empty();
        }
    }
    
    @Transactional(readOnly = true)
    public List<BookResponseDto> findByAuthorId(Long authorId) {
        
        List<BookEntity> books = bookRepository.findByAuthorIdWithAuthor(authorId);
        return bookMapper.toResponseDtoList(books);
    }
    
    @Transactional(readOnly = true)
    public List<BookResponseDto> findByAuthorName(String authorName) {
        
        if (!StringUtils.hasText(authorName)) {
            return List.of();
        }
        
        List<BookEntity> books = bookRepository.findByAuthorNameContainingIgnoreCase(authorName);
        return bookMapper.toResponseDtoList(books);
    }
    
    @Transactional(readOnly = true)
    public List<BookResponseDto> findBooksWithAuthor() {
        
        List<BookEntity> books = bookRepository.findBooksWithAuthor();
        return bookMapper.toResponseDtoList(books);
    }
    
    @Transactional(readOnly = true)
    public List<BookResponseDto> findBooksWithoutAuthor() {
        
        List<BookEntity> books = bookRepository.findBooksWithoutAuthor();
        return bookMapper.toResponseDtoList(books);
    }
    
    @Transactional(readOnly = true)
    public Long countBooksByAuthorId(Long authorId) {
        Long count = bookRepository.countBooksByAuthorId(authorId);
        return count;
    }
}
