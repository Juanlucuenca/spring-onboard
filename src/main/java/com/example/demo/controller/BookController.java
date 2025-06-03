package com.example.demo.controller;

import com.example.demo.dto.book.BookCreateDto;
import com.example.demo.dto.book.BookResponseDto;
import com.example.demo.service.BookService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Libros", description = "Endpoints de gestión de libros (GET endpoints son públicos, otros requieren autenticación)")
public class BookController {
    
    private final BookService bookService;
    
    @GetMapping 
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {
        List<BookResponseDto> books = bookService.findAll();
        return ResponseEntity.ok(books);
    }
    
    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BookResponseDto> createBook(
            @Valid @RequestBody BookCreateDto createDto) {
        BookResponseDto createdBook = bookService.create(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBookById(
            @Parameter(description = "Book ID", example = "1")
            @PathVariable Long id) {        
        Optional<BookResponseDto> book = bookService.findById(id);
        return book.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BookResponseDto> updateBook(
            @Parameter(description = "Book ID", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody BookCreateDto updateDto) {
        
        Optional<BookResponseDto> updatedBook = bookService.update(id, updateDto);
        return updatedBook.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "Book ID", example = "1")
            @PathVariable Long id) {
        
        boolean deleted = bookService.delete(id);
        return deleted ? ResponseEntity.noContent().build() 
                      : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/search/title")
    public ResponseEntity<BookResponseDto> findBookByTitle(
            @Parameter(description = "Title to search for", example = "Cien años de soledad")
            @RequestParam String title) {
        
        Optional<BookResponseDto> book = bookService.findByTitle(title);
        return book.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/search/author-id/{authorId}")
    public ResponseEntity<List<BookResponseDto>> getBooksByAuthorId(
            @Parameter(description = "Author ID", example = "1")
            @PathVariable Long authorId) {
        
        List<BookResponseDto> books = bookService.findByAuthorId(authorId);
        return ResponseEntity.ok(books);
    }
    
    @GetMapping("/search/author-name")
    public ResponseEntity<List<BookResponseDto>> getBooksByAuthorName(
            @Parameter(description = "Author name to search for", example = "Garcia")
            @RequestParam String authorName) {
        
        List<BookResponseDto> books = bookService.findByAuthorName(authorName);
        return ResponseEntity.ok(books);
    }
    
    @GetMapping("/with-author")
    public ResponseEntity<List<BookResponseDto>> getBooksWithAuthor() {
        
        List<BookResponseDto> books = bookService.findBooksWithAuthor();
        return ResponseEntity.ok(books);
    }
    
    @GetMapping("/without-author")
    public ResponseEntity<List<BookResponseDto>> getBooksWithoutAuthor() {
        
        List<BookResponseDto> books = bookService.findBooksWithoutAuthor();
        return ResponseEntity.ok(books);
    }
    
    @GetMapping("/stats/author/{authorId}")
    public ResponseEntity<Long> countBooksByAuthor(
            @Parameter(description = "Author ID", example = "1")
            @PathVariable Long authorId) {
        
        Long count = bookService.countBooksByAuthorId(authorId);
        return ResponseEntity.ok(count);
    }
}
