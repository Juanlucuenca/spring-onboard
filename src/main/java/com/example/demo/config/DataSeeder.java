package com.example.demo.config;

import com.example.demo.persistance.model.AuthorEntity;
import com.example.demo.persistance.model.BookEntity;
import com.example.demo.persistance.repository.AuthorRepository;
import com.example.demo.persistance.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public void run(String... args) {
        seedData();
    }

    private void seedData() {
        // Limpiar datos existentes
        bookRepository.deleteAll();
        authorRepository.deleteAll();

        // Crear autores
        AuthorEntity author1 = new AuthorEntity();
        author1.setName("Gabriel García Márquez");
        author1.setEmail("garcia.marquez@example.com");
        author1.setWebsite("https://www.gabrielgarciamarquez.com");
        author1 = authorRepository.save(author1);

        AuthorEntity author2 = new AuthorEntity();
        author2.setName("Isabel Allende");
        author2.setEmail("isabel.allende@example.com");
        author2.setWebsite("https://www.isabelallende.com");
        author2 = authorRepository.save(author2);

        // Crear libros
        BookEntity book1 = new BookEntity();
        book1.setTitle("Cien años de soledad");
        book1.setAuthorId(author1.getId());
        bookRepository.save(book1);

        BookEntity book2 = new BookEntity();
        book2.setTitle("El amor en los tiempos del cólera");
        book2.setAuthorId(author1.getId());
        bookRepository.save(book2);

        BookEntity book3 = new BookEntity();
        book3.setTitle("La casa de los espíritus");
        book3.setAuthorId(author2.getId());
        bookRepository.save(book3);

        BookEntity book4 = new BookEntity();
        book4.setTitle("Paula");
        book4.setAuthorId(author2.getId());
        bookRepository.save(book4);
    }
} 