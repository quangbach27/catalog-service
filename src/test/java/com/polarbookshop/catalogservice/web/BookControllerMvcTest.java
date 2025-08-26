package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.BookService;
import com.polarbookshop.catalogservice.domain.exceptions.BookNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerMvcTest {
    @Autowired
    private MockMvc mockMvc;
    
    @MockitoBean
    private BookService bookService;
    
    @Test
    void whenGetBookNotExistingThenShouldReturn404() throws Exception {
        // Given
        String isbn = "73737313940";
        given(bookService.viewBookDetails(isbn))
                .willThrow(BookNotFoundException.class);
        
        // Then / Then
        mockMvc
                .perform(get("/books/" + isbn))
                .andExpect(status().isNotFound());
    }

}