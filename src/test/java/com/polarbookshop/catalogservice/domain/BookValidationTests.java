package com.polarbookshop.catalogservice.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


public class BookValidationTests {
    private static Validator validator;
    
    @BeforeAll
    static void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    
    @Test
    void whenAllFieldCorrectThenValidationSucceeds() {
        Book book = new Book("1234567890", "Title", "Author", 9.99);
        assertThat(validator.validate(book)).isEmpty();
    }
    
    @Test
    void whenIsbnDefinedButIncorrectThenValidationFails() {
        var book = new Book("a123456789", "Title", "Author", 9.99);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The ISBN format must be valid.");
    }
}
