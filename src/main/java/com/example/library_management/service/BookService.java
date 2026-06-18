package com.example.library_management.service;

import com.example.library_management.dto.BookCreateDTO;
import com.example.library_management.entity.Book;
import com.example.library_management.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final String uploadDir = "uploads/";

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        // Create upload directory if it doesn't exist
        try {
            Files.createDirectories(Paths.get(uploadDir));
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }

    public Book createBook(BookCreateDTO dto) {
        String fileName = null;
        MultipartFile file = dto.getCoverImage();

        if (file != null && !file.isEmpty()) {
            fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            try {
                Path path = Paths.get(uploadDir + fileName);
                Files.copy(file.getInputStream(), path);
            } catch (IOException e) {
                throw new RuntimeException("Could not store file " + fileName, e);
            }
        }

        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setStock(dto.getStock());
        book.setCoverUrl(fileName != null ? uploadDir + fileName : null);

        return bookRepository.save(book);
    }
}
