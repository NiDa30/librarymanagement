package com.example.library_management;

import com.example.library_management.entity.Book;
import com.example.library_management.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testCreateBookWithImage() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "coverImage",
                "test-cover.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "fake image content".getBytes()
        );

        mockMvc.perform(multipart("/api/books")
                .file(file)
                .param("title", "Spring Boot in Action")
                .param("author", "Craig Walls")
                .param("stock", "10"))
                .andExpect(status().isCreated());

        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(1);
        Book book = books.get(0);
        assertThat(book.getTitle()).isEqualTo("Spring Boot in Action");
        assertThat(book.getCoverUrl()).contains("test-cover.jpg");

        // Check if file exists
        Path path = Paths.get(book.getCoverUrl());
        assertThat(Files.exists(path)).isTrue();
    }
}
