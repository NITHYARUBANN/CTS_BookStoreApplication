package com.example.cartservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Cart class represents an entity for storing cart details. This class is
 * annotated with JPA and validation annotations to ensure proper persistence
 * and validation of data.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull(message = "Book ID is mandatory")
	private int bookId;

	@NotBlank(message = "Name is mandatory")
	private String name;

	@NotNull(message = "Quantity is mandatory")
	@Min(value = 1, message = "Quantity must be greater than or equal to 1")
	private Integer quantity;

	@NotNull(message = "Price is mandatory")
	private Double price;

	@NotNull(message = "User ID is mandatory")
	private Integer userId;
}
