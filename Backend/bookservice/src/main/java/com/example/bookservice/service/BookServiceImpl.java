package com.example.bookservice.service;

import com.example.bookservice.entity.Book;
import com.example.bookservice.exception.BookNotFoundException;
import com.example.bookservice.exception.ResourceNotFoundException;
import com.example.bookservice.repository.BookRepository;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Service implementation for managing books.
 */
@Service
public class BookServiceImpl implements BookService {

	private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

	private final BookRepository bookRepository;

	/**
	 * Constructor for BookServiceImpl.
	 *
	 * @param bookRepository the book repository
	 */
	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	/**
	 * Get all books.
	 *
	 * @return a list of all books.
	 */
	@Override
	public List<Book> getAllBooks() {
		logger.info("Fetching all books");
		return bookRepository.findAll();
	}

	/**
	 * Get a book by its ID.
	 *
	 * @param id the ID of the book to retrieve.
	 * @return the book with the specified ID, or null if not found.
	 */
	@Override
	public Book getBookById(int id) {
		logger.info("Fetching book with ID: {}", id);
		return bookRepository.findById(id).orElse(null);
	}

	/**
	 * Add a new book.
	 *
	 * @param book the book to create.
	 * @return the created book.
	 */
	@Override
	public Book createBook(@Valid Book book) {
		logger.info("Creating new book with title: {}", book.getTitle());
		return bookRepository.save(book);
	}

	/**
	 * Update an existing book.
	 *
	 * @param id   the ID of the book to update.
	 * @param book the updated book information.
	 * @return the updated book, or null if not found.
	 * @throws BookNotFoundException if the book is not found
	 */
	@Override
	public Book updateBook(int id, @Valid Book book) {
		logger.info("Updating book with ID: {}", id);
		Book existingBook = bookRepository.findById(id).orElse(null);
		if (existingBook != null) {
			existingBook.setTitle(book.getTitle());
			existingBook.setAuthor(book.getAuthor());
			existingBook.setCategory(book.getCategory());
			existingBook.setPrice(book.getPrice());
			existingBook.setQuantity(book.getQuantity());
			logger.info("Book with ID: {} updated successfully", id);
			return bookRepository.save(existingBook);
		}
		logger.warn("Book with ID: {} not found", id);
		throw new BookNotFoundException("No book is found with the id: " + id);
	}

	/**
	 * Delete a book by its ID.
	 *
	 * @param id the ID of the book to delete.
	 * @throws ResourceNotFoundException if the book is not found
	 */
	@Override
	public void deleteBook(int id) {
		logger.info("Deleting book with ID: {}", id);
		if (!bookRepository.existsById(id)) {
			logger.warn("Book with ID: {} not found", id);
			throw new ResourceNotFoundException("There is no Book with the id: " + id);
		}
		bookRepository.deleteById(id);
	}

	/**
	 * Search books by category.
	 *
	 * @param category the category to search books by.
	 * @return a list of books that belong to the specified category.
	 */
	@Override
	public List<Book> searchBooksByCategory(String category) {
		logger.info("Searching books by category: {}", category);
		return bookRepository.findByCategory(category);
	}

	/**
	 * Search books by author.
	 *
	 * @param author the author to search books by.
	 * @return a list of books that belong to the specified author.
	 */
	@Override
	public List<Book> searchBooksByAuthor(String author) {
		logger.info("Searching books by author: {}", author);
		return bookRepository.findByAuthor(author);
	}

	/**
	 * Search books by title.
	 *
	 * @param title the title to search books by.
	 * @return a list of books that belong to the specified title.
	 */
	@Override
	public List<Book> searchBooksByTitle(String title) {
		logger.info("Searching books by title: {}", title);
		return bookRepository.findByTitle(title);
	}
}