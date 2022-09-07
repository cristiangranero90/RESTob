package com.example.obrestdatajpa.controllers;

import com.example.obrestdatajpa.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    @LocalServerPort
    private int port;
    private TestRestTemplate testRestTemplate;
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @Test
    void findAll() {
        ResponseEntity<Book[]> response =
                testRestTemplate.getForEntity("/api/books", Book[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue());

        List<Book> books = Arrays.asList(response.getBody());
        System.out.println(books.size());
    }

    @Test
    void findById() {
        ResponseEntity<Book> response =
                testRestTemplate.getForEntity("/api/books/1", Book.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @DisplayName("Comprueba save() desde los controladores de Spring REST")
    @Test
    void save() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                {
                    "title": "SomeTitle",
                    "author": "AnotherAuthor",
                    "pages": 343,
                    "price": 9.99,
                    "releaseDate": "2020-12-06",
                    "online": true
                }
                """;

        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<Book> response = testRestTemplate.exchange("/api/books/", HttpMethod.POST, request, Book.class);

        Book result = response.getBody();

        assertEquals(1L, result.getId());
        assertEquals("SomeTitle", result.getTitle());
    }

    @Test
    void update() {

    }

    @Test
    void delete() {
    }

    @Test
    void deleteAll() {
    }
}