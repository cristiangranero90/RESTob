package com.example.obrestdatajpa.controllers;

import com.example.obrestdatajpa.entity.Book;
import com.example.obrestdatajpa.repositories.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

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
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("api/books")
    public Book save(@RequestBody Book book){
        return bookRepository.save(book);
    }


}
