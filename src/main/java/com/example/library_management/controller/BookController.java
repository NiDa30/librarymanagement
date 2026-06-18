package com.example.library_management.controller;

import com.example.library_management.dto.BookCreateDTO;
import com.example.library_management.entity.Book;
import com.example.library_management.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@ModelAttribute BookCreateDTO dto) {
        Book createdBook = bookService.createBook(dto);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }
}
