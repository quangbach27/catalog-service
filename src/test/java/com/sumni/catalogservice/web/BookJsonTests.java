package com.sumni.catalogservice.web;

import com.sumni.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

@JsonTest
public class BookJsonTests {
    @Autowired
    private JacksonTester<Book> json;
    
    @Test
    void testSerialize() throws IOException {
        var book = Book.of("1234567890", "Title", "Author", 9.90);
        var jsonContent = json.write(book);
        assertThat(jsonContent).hasJsonPathStringValue("@.isbn", book.isbn());
        assertThat(jsonContent).hasJsonPathStringValue("@.title", book.title());
        assertThat(jsonContent).hasJsonPathStringValue("@.author", book.author());
        assertThat(jsonContent).hasJsonPathNumberValue("@.price", book.price());
    }
}
