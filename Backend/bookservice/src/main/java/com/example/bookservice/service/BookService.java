
package com.example.bookservice.service;

import com.example.bookservice.entity.Book;
import java.util.List;

/**
 * Service interface for managing books.
 */
public interface BookService {
    List<Book> getAllBooks();
    Book getBookById(int id);
    Book createBook(Book book);
    Book updateBook(int id, Book book);
    void deleteBook(int id);
    List<Book> searchBooksByCategory(String category);
    List<Book> searchBooksByAuthor(String author);
    List<Book> searchBooksByTitle(String title);

}