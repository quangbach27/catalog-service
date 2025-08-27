package com.polarbookshop.catalogservice.domain;

import com.polarbookshop.catalogservice.domain.exceptions.BookAlreadyExistsException;
import com.polarbookshop.catalogservice.domain.exceptions.BookNotFoundException;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

@ExtendWith(MockitoExtension.class)
class BookServiceTests {
    @Mock
    private BookRepository bookRepository;
    
    @InjectMocks
    private BookService bookService;
    
    // ViewBookList
    @Test
    void shouldReturnAllBooks_whenViewBookListIsCalled() {
        // Given
        Book book1 = validBook("1234561232");
        Book book2 = validBook("9876543210");
        given(bookRepository.findAll()).willReturn(List.of(book1, book2));
        
        // When
        Iterable<Book> books = bookService.viewBookList();
        
        // Then
        then(books).contains(book1, book2);
    }
    
    // ViewBookDetails
    @Test
    void shouldFindBook_whenBookExists() {
        // Given
        String bookIsbn = "1234561232";
        Book repoBook = validBook(bookIsbn);
        given(this.bookRepository.findByIsbn(bookIsbn)).willReturn(Optional.of(repoBook));
        
        // When
        Book book = this.bookService.viewBookDetails(bookIsbn);
        
        // Then
        then(book).isEqualTo(repoBook);
    }
    
    @Test
    void shouldThrowException_whenBookNotFound() {
        // Given
        String bookIsbn = "1234561232";
        given(this.bookRepository.findByIsbn(bookIsbn)).willReturn(Optional.empty());
        
        // When
        ThrowableAssert.ThrowingCallable action = () -> this.bookService.viewBookDetails(bookIsbn);
        
        // Then
        thenThrownBy(action)
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("The book with ISBN " + bookIsbn + " was not found.");
    }
    
    @Test
    void shouldThrowException_whenBookToCreateAlreadyExists() {
        // Given
        String bookIsbn = "1234561232";
        Book bookToUpdate = validBook(bookIsbn);
        given(this.bookRepository.existsByIsbn(bookIsbn)).willReturn(true);
        
        // When
        ThrowableAssert.ThrowingCallable action = () -> this.bookService.addBookToCatalog(bookToUpdate);
        
        // Then
        thenThrownBy(action)
                .isInstanceOf(BookAlreadyExistsException.class)
                .hasMessage("A book with ISBN " + bookIsbn + " already exists.");
    }
    
    @Test
    void shouldAddBook_whenBookToCreateNotExists() {
        // Given
        String bookIsbn = "1234561232";
        Book bookToUpdate = validBook(bookIsbn);
        given(this.bookRepository.existsByIsbn(bookIsbn)).willReturn(false);
        given(this.bookRepository.save(bookToUpdate)).willReturn(bookToUpdate);
        
        // When
        Book newBook = this.bookService.addBookToCatalog(bookToUpdate);
        
        // Then
        then(newBook).isNotNull();
        then(newBook.isbn()).isEqualTo(bookIsbn);
        then(newBook.title()).isEqualTo(bookToUpdate.title());
        then(newBook.author()).isEqualTo(bookToUpdate.author());
        then(newBook.price()).isEqualTo(bookToUpdate.price());
    }
    
    
    
    private Book validBook(String bookIsbn) {
        return Book.of(bookIsbn, "Title", "Author", 9.90);
    }
    
}