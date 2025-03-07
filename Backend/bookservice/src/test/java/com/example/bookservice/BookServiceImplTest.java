package com.example.bookservice;

import com.example.bookservice.entity.Book;
import com.example.bookservice.repository.BookRepository;
import com.example.bookservice.service.BookServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks() {
        Book book1 = new Book(1, "Title1", "Author1", "Category1", 100.0, 10);
        Book book2 = new Book(2, "Title2", "Author2", "Category2", 200.0, 20);
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = bookService.getAllBooks();
        assertEquals(2, books.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testGetBookById() {
        Book book = new Book(1, "Title1", "Author1", "Category1", 100.0, 10);
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        Book foundBook = bookService.getBookById(1);
        assertNotNull(foundBook);
        assertEquals("Title1", foundBook.getTitle());
        verify(bookRepository, times(1)).findById(1);
    }

    @Test
    void testCreateBook() {
        Book book = new Book(1, "Title1", "Author1", "Category1", 100.0, 10);
        when(bookRepository.save(book)).thenReturn(book);

        Book createdBook = bookService.createBook(book);
        assertNotNull(createdBook);
        assertEquals("Title1", createdBook.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testUpdateBook() {
        Book existingBook = new Book(1, "Title1", "Author1", "Category1", 100.0, 10);
        Book updatedBook = new Book(1, "UpdatedTitle", "UpdatedAuthor", "UpdatedCategory", 150.0, 15);
        when(bookRepository.findById(1)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(existingBook)).thenReturn(updatedBook);

        Book result = bookService.updateBook(1, updatedBook);
        assertNotNull(result);
        assertEquals("UpdatedTitle", result.getTitle());
        verify(bookRepository, times(1)).findById(1);
        verify(bookRepository, times(1)).save(existingBook);
    }

    @Test
    void testDeleteBook() {
        doNothing().when(bookRepository).deleteById(1);

        bookService.deleteBook(1);
        verify(bookRepository, times(1)).deleteById(1);
    }

    @Test
    void testSearchBooksByCategory() {
        Book book1 = new Book(1, "Title1", "Author1", "Category1", 100.0, 10);
        Book book2 = new Book(2, "Title2", "Author2", "Category1", 200.0, 20);
        when(bookRepository.findByCategory("Category1")).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = bookService.searchBooksByCategory("Category1");
        assertEquals(2, books.size());
        verify(bookRepository, times(1)).findByCategory("Category1");
    }
}