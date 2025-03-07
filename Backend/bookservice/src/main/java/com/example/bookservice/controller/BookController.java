package com.example.bookservice.controller;

import com.example.bookservice.entity.Book;
import com.example.bookservice.exception.ResourceNotFoundException;
import com.example.bookservice.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing books in the book service. Provides end points
 * for CRUD operations and searching books by category.
 */
@RestController
@RequestMapping("/books")
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	/**
	 * GET /books/getAll : Get all books.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the list of books in
	 *         body.
	 */
	@GetMapping("/getAll")
	public ResponseEntity<List<Book>> getAllBooks() {
		return ResponseEntity.ok(bookService.getAllBooks());
	}

	/**
	 * GET /books/{id} : Get the "id" book.
	 *
	 * @param id the id of the book to retrieve.
	 * @return the ResponseEntity with status 200 (OK) and with body the book, or
	 *         with status 404 (Not Found).
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable int id) {
		Book book = bookService.getBookById(id);
		if (book == null) {
			throw new ResourceNotFoundException("No Book is found with id " + id);
		}
		return ResponseEntity.ok(book);
	}

	/**
	 * POST /books/add : Create a new book.
	 *
	 * @param book the book to create.
	 * @return the ResponseEntity with status 200 (OK) and with body the new book.
	 */
	@PostMapping("/add")
	public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
		return ResponseEntity.ok(bookService.createBook(book));
	}

	/**
	 * PUT /books/edit/{id} : Updates an existing book.
	 *
	 * @param id   the id of the book to update.
	 * @param book the book to update.
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         book.
	 */
	@PutMapping("/edit/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable int id, @Valid @RequestBody Book book) {
		return ResponseEntity.ok(bookService.updateBook(id, book));
	}

	/**
	 * DELETE /books/delete/{id} : Delete the "id" book.
	 *
	 * @param id the id of the book to delete.
	 * @return the ResponseEntity with status 204 (No Content).
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Map<String, String>> deleteBook(@PathVariable int id) {
		bookService.deleteBook(id);
		Map<String, String> response = new HashMap<>();
		response.put("message", "Book deleted successfully.");
		return ResponseEntity.ok(response);
	}

	/**
	 * GET /books/search : Search books by category.
	 *
	 * @param category the category to search books by.
	 * @return the ResponseEntity with status 200 (OK) and the list of books in
	 *         body.
	 */
	@GetMapping("/search")
	public ResponseEntity<List<Book>> searchBooksByCategory(@RequestParam String category) {
		return ResponseEntity.ok(bookService.searchBooksByCategory(category));
	}

	/**
	 * GET /books/searchByAuthor : Search books by Author.
	 *
	 * @param author the author to search books by.
	 * @return the ResponseEntity with status 200 (OK) and the list of books in
	 *         body.
	 */
	@GetMapping("/searchByAuthor")
	public ResponseEntity<List<Book>> searchBooksByAuthor(@RequestParam String author) {
		return ResponseEntity.ok(bookService.searchBooksByAuthor(author));
	}

	/**
	 * GET /books/searchByTitle : Search book by title.
	 *
	 * @param title the title to search books by.
	 * @return the ResponseEntity with status 200 (OK) and the list of books in
	 *         body.
	 */
	@GetMapping("/searchByTitle")
	public ResponseEntity<List<Book>> searchBooksByTitle(@RequestParam String title) {
		return ResponseEntity.ok(bookService.searchBooksByTitle(title));
	}

}