package com.example.book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookRepository bookRepository;

    BookController(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @GetMapping("/{requestedId}")
    ResponseEntity<Book> findById(@PathVariable Long requestedId){
        java.util.Optional<Book> responseEntity = bookRepository.findById(requestedId);
        if(responseEntity.isPresent())
            return ResponseEntity.ok(responseEntity.get());
        else
            return ResponseEntity.notFound().build();
    }
}
