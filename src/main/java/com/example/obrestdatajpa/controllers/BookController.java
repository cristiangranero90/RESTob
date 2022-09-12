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
    public ResponseEntity<Book> save(@RequestBody Book book){
        if (book.getId() != null){
            log.warn("The book already exist. ");
            return ResponseEntity.badRequest().build();
        }
        else{
            return ResponseEntity.ok(bookRepository.save(book));
        }
    }

    /**
     * Actualizar un libro existente
     *
     * @return
     */
    @PutMapping("/api/books")
    public ResponseEntity<Book> update(@RequestBody Book book){
        if(book.getId() == null){
            log.warn("Trying to update a non existent book.");
            return ResponseEntity.badRequest().build();
        }
        else {
            return ResponseEntity.ok(bookRepository.save(book));
        }
    }

    @DeleteMapping("/api/books/{id}")
    public ResponseEntity<Book> delete(@PathVariable Long id){
        if(bookRepository.existsById(id)){
            bookRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/api/books")
    public ResponseEntity<Book> deleteAll(){
        log.info("Deleting all the books in the data base");
        bookRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

}
