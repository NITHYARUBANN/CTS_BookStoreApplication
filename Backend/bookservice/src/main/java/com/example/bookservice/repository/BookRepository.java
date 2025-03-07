package com.example.bookservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookservice.entity.Book;

/**
 * Repository interface for managing Book entities. Extends JpaRepository to
 * provide CRUD operations and custom query methods.
 */
public interface BookRepository extends JpaRepository<Book, Integer> {
	/**
	 * Finds books by their category.
	 *
	 * @param category the category of the books to find.
	 * @return a list of books that belong to the specified category.
	 */
	List<Book> findByCategory(String category);

	/**
	 * Finds books by their author.
	 *
	 * @param author the author of the books to find.
	 * @return a list of books that belong to the specified author.
	 */
	List<Book> findByAuthor(String author);

	/**
	 * Finds books by the title.
	 *
	 * @param title the title of the book to find the book.
	 * @return book that belong to the specified title.
	 */
	List<Book> findByTitle(String title);

}
