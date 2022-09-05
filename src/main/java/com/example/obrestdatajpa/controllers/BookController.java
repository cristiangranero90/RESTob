package com.example.obrestdatajpa.controllers;

import com.example.obrestdatajpa.entity.Book;
import com.example.obrestdatajpa.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private final Logger log = LoggerFactory.getLogger(BookController.class);
    private BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/api/books")
    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    @GetMapping("/api/books/{id}")
    public ResponseEntity<Book> findById(@PathVariable Long id){

        Optional<Book> bookOpt = bookRepository.findById(id);

        if (bookOpt.isPresent()){
            return ResponseEntity.ok(bookOpt.get());
        }
        else{
            log.warn("The Book do not exist on the DB");
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("api/books")
    public Book save(@RequestBody Book book){
        return bookRepository.save(book);
    }

    /**
     * Actualizar un libro existente
     *
     * @return
     */
    @PutMapping("/api/books")
    public Book update(@RequestBody Book book){
        return bookRepository.save(book);
    }

}
