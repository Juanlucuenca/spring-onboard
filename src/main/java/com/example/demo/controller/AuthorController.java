package com.example.demo.controller;

import com.example.demo.dto.author.AuthorCreateDto;
import com.example.demo.dto.author.AuthorResponseDto;
import com.example.demo.service.AuthorService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
@Tag(name = "Autores", description = "Endpoints de gestión de autores (Todos los endpoints requieren autenticación)")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("isAuthenticated()")
public class AuthorController {
    
    private final AuthorService authorService;
    
    @GetMapping
    public ResponseEntity<List<AuthorResponseDto>> getAllAuthors() {
        List<AuthorResponseDto> authors = authorService.findAll();
        return ResponseEntity.ok(authors);
    }
    
    @PostMapping
    public ResponseEntity<AuthorResponseDto> createAuthor(
            @Valid @RequestBody AuthorCreateDto createDto) {
        AuthorResponseDto createdAuthor = authorService.create(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> getAuthorById(
            @Parameter(description = "Author ID", example = "1")
            @PathVariable Long id) {
        
        Optional<AuthorResponseDto> author = authorService.findById(id);
        return author.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> updateAuthor(
            @Parameter(description = "Author ID", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody AuthorCreateDto updateDto) {
        
        Optional<AuthorResponseDto> updatedAuthor = authorService.update(id, updateDto);
        return updatedAuthor.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(
            @Parameter(description = "Author ID", example = "1")
            @PathVariable Long id) {
        
        boolean deleted = authorService.delete(id);
        return deleted ? ResponseEntity.noContent().build() 
                      : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/search/name") 

    public ResponseEntity<List<AuthorResponseDto>> searchAuthorsByName(
            @Parameter(description = "Name to search for", example = "Garcia")
            @RequestParam String name) {
        
        List<AuthorResponseDto> authors = authorService.searchByName(name);
        return ResponseEntity.ok(authors);
    }
    
    @GetMapping("/search/email")
    public ResponseEntity<AuthorResponseDto> findAuthorByEmail(
            @Parameter(description = "Email to search for", example = "gabriel.garcia@example.com")
            @RequestParam String email) {
        
        Optional<AuthorResponseDto> author = authorService.findByEmail(email);
        return author.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/ordered")
    public ResponseEntity<List<AuthorResponseDto>> getAuthorsOrderedByName() {
        
        List<AuthorResponseDto> authors = authorService.findAllOrderedByName();
        return ResponseEntity.ok(authors);
    }
}
