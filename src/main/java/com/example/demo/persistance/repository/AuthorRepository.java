package com.example.demo.persistance.repository;

import com.example.demo.persistance.model.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    
    /**
     * Encontrar autores por nombre que contenga el string, sin importar mayusculas o minusculas
     */
    @Query("SELECT a FROM AuthorEntity a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<AuthorEntity> findByNameContainingIgnoreCase(@Param("name") String name);
    
    /**
     * Encontrar autor por email
     */
    Optional<AuthorEntity> findByEmail(String email);
    
    /**
     * Verificar si el email existe
     */
    boolean existsByEmail(String email);
    
    /**
     * Verificar si el email existe excepto para un autor específico (para la validación de actualización)
     */
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM AuthorEntity a WHERE a.email = :email AND a.id != :authorId")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("authorId") Long authorId);
    
    /**
     * Encontrar todos los autores ordenados por nombre ordenados alfabeticamente
     */
    @Query("SELECT a FROM AuthorEntity a ORDER BY a.name ASC")
    List<AuthorEntity> findAllOrderedByName();
}
