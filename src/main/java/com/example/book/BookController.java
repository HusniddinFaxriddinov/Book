package com.example.book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookRepository bookRepository;

    BookController(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    // ================ Get Controllers ==============

    @GetMapping("/{requestedId}")
    ResponseEntity<Book> findById(@PathVariable Long requestedId, Principal principal){
        Book book = findBook(requestedId, principal);
        if(book != null)
            return ResponseEntity.ok(book);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping()
    ResponseEntity<List<Book>> findAll(Pageable pageable, Principal principal){
        Page<Book> page = bookRepository.findByAuthor(principal.getName(),
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "price"))
                ));
        return ResponseEntity.ok(page.getContent());
    }


    // ============== Post Controller ===============
    @PostMapping
    ResponseEntity<Void> createBook(@RequestBody Book bookCreation, UriComponentsBuilder ucb, Principal principal){
        Book createdBook = new Book(bookCreation.id(), bookCreation.price(), principal.getName());
        Book savedBook = bookRepository.save(createdBook);
        URI locationOfNewBook = ucb
                .path("/books/{id}")
                .buildAndExpand(savedBook.id())
                .toUri();

        return ResponseEntity.created(locationOfNewBook).build();
    }


    // ================= Put Controller =================
    @PutMapping("/{requiredId}")
    ResponseEntity<Void> putBook(@PathVariable long requiredId ,@RequestBody Book bookUpdate, Principal principal){
        Book currentBook = findBook(requiredId, principal);
        Book updatedBook = new Book(currentBook.id(), bookUpdate.price(), principal.getName());
        bookRepository.save(updatedBook);
        return ResponseEntity.noContent().build();
    }

    // ============= Delete Controller ==================
    @DeleteMapping("/{requiredId}")
    ResponseEntity<Book> deleteBook(@PathVariable long requiredId, Principal principal){
        if(bookRepository.existsByIdAndAuthor(requiredId, principal.getName())){
            bookRepository.deleteByIdAndAuthor(requiredId, principal.getName());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }



    Book findBook(long id, Principal principal){
        Book book = bookRepository.findByIdAndAuthor(id, principal.getName());
        return book;
    }
}
