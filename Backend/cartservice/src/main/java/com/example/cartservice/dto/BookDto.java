package com.example.cartservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for Book entity. This class is used to transfer
 * book data between different layers of the application.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
	private int id;
	private String title;
	private String author;
	private String category;
	private Double price;
	private Integer quantity;
}