package com.example.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;

import java.security.Principal;

public interface BookRepository extends CrudRepository<Book, Long> {
    Book findByIdAndAuthor(long id, String author);
    Page<Book> findByAuthor(String author, PageRequest pageRequest);
    void deleteByIdAndAuthor(long id, String author);
    boolean existsByIdAndAuthor(long id, String author);
}
