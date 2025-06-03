package com.example.demo.persistance.repository;

import com.example.demo.persistance.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    
    /**
     * Encontrar libro por titulo exacto
     */
    Optional<BookEntity> findByTitle(String title);
    
    /**
     * Verificar si el titulo existe
     */
    boolean existsByTitle(String title);
    
    /**
     * Verificar si el titulo existe excepto para un libro específico UPDATE
     */
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM BookEntity b WHERE b.title = :title AND b.id != :bookId")
    boolean existsByTitleAndIdNot(@Param("title") String title, @Param("bookId") Long bookId);
    
    /**
     * Encontrar libros por ID de autor
     */
    List<BookEntity> findByAuthorId(Long authorId);
    
    /**
     * Encontrar libros por nombre de autor usando JOIN
     */
    @Query("SELECT b FROM BookEntity b JOIN b.author a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :authorName, '%'))")
    List<BookEntity> findByAuthorNameContainingIgnoreCase(@Param("authorName") String authorName);
    
    /**
     * Encontrar libros que tienen un autor asignado
     */
    @Query("SELECT b FROM BookEntity b WHERE b.authorId IS NOT NULL")
    List<BookEntity> findBooksWithAuthor();
    
    /**
     * Encontrar libros que no tienen un autor asignado
     */
    @Query("SELECT b FROM BookEntity b WHERE b.authorId IS NULL")
    List<BookEntity> findBooksWithoutAuthor();
    
    /**
     * Contar libros por ID de autor
     */
    @Query("SELECT COUNT(b) FROM BookEntity b WHERE b.authorId = :authorId")
    Long countBooksByAuthorId(@Param("authorId") Long authorId);
    
    /**
     * Encontrar todos los libros con sus autores
     */
    @Query("SELECT b FROM BookEntity b LEFT JOIN FETCH b.author")
    List<BookEntity> findAllWithAuthors();
    
    /**
     * Encontrar libros por ID de autor
     */
    @Query("SELECT b FROM BookEntity b LEFT JOIN FETCH b.author WHERE b.authorId = :authorId")
    List<BookEntity> findByAuthorIdWithAuthor(@Param("authorId") Long authorId);
}
