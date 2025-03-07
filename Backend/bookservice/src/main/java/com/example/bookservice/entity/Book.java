package com.example.bookservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

/**
 * Entity class representing a Book.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    /**
     * Unique identifier for the book.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Title of the book.
     * This field is mandatory.
     */
    @NotBlank(message = "Title is mandatory")
    private String title;

    /**
     * Author of the book.
     * This field is mandatory.
     */
    @NotBlank(message = "Author is mandatory")
    private String author;

    /**
     * Category of the book.
     * This field is mandatory.
     */
    @NotBlank(message = "Category is mandatory")
    private String category;

    /**
     * Price of the book.
     * This field is mandatory and must be greater than or equal to 0.
     */
    @NotNull(message = "Price is mandatory")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Double price;

    /**
     * Quantity of the book in stock.
     * This field is mandatory and must be greater than or equal to 0.
     */
    @NotNull(message = "Quantity is mandatory")
    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    private Integer quantity;
}