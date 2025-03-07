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
 * The PurchaseOrder class represents an entity for storing purchase order
 * details. This class is annotated with JPA and validation annotations to
 * ensure proper persistence and validation of data.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;

	@NotNull(message = "User ID is mandatory")
	private Integer userId;

	@NotBlank(message = "Username is mandatory")
	private String username;

	@NotBlank(message = "Order details are mandatory")
	private String orderDetails;

	@NotNull(message = "Total cost is mandatory")
	@Min(value = 0, message = "Total cost must be greater than or equal to 0")
	private Double totalCost;

	@NotBlank(message = "Address is mandatory")
	private String address;
}