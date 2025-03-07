package com.example.cartservice.controller;

import com.example.cartservice.entity.Cart;
import jakarta.validation.Valid;
import com.example.cartservice.entity.PurchaseOrder;
import com.example.cartservice.exception.ResourceNotFoundException;
import com.example.cartservice.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The CartController class handles HTTP requests related to cart operations.
 * This class provides endpoints for adding items to the cart, retrieving cart
 * items, deleting cart items, checking out, and retrieving order history.
 */
@RestController
@RequestMapping("/carts")
public class CartController {

	private final CartService cartService;

	/**
	 * Constructor for CartController.
	 *
	 * @param cartService the cart service
	 */
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	/**
	 * Retrieves all items in the cart for a specific user.
	 *
	 * @param userId the ID of the user
	 * @return ResponseEntity containing the list of all cart items
	 */
	@GetMapping("/getAll/{userId}")
	public ResponseEntity<List<Cart>> getAllItems(@PathVariable int userId) {
		return ResponseEntity.ok(cartService.getAllItems(userId));
	}

	/**
	 * Retrieves a specific cart item by its ID.
	 *
	 * @param id the ID of the cart item to retrieve
	 * @return ResponseEntity containing the cart item, or a NOT_FOUND status if the
	 *         item is not found
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Cart> getItemById(@PathVariable int id) {
		Cart cart = cartService.getItemById(id);
		if (cart == null) {
			throw new ResourceNotFoundException("No Item is found with id " + id);
		}
		return ResponseEntity.ok(cart);
	}

	/**
	 * Adds a book to the cart.
	 *
	 * @param bookId   the ID of the book to add
	 * @param quantity the quantity of the book to add
	 * @param userId   the ID of the user
	 * @return ResponseEntity containing the updated cart, or a NOT_FOUND status if
	 *         the book is not found or insufficient quantity
	 */
	@PostMapping("/add/{userId}")
	public ResponseEntity<Cart> addBookToCart(@RequestParam int bookId, @RequestParam int quantity,
			@PathVariable int userId) {
		Cart cart = cartService.addBookToCart(bookId, quantity, userId);
		if (cart == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(cart);
	}

	/**
	 * Deletes a specific item from the cart.
	 *
	 * @param userId the ID of the user
	 * @param itemId the ID of the item to delete
	 * @return ResponseEntity with no content
	 */
	@DeleteMapping("/delete/{userId}/{itemId}")
	public ResponseEntity<Void> deleteCartItem(@PathVariable int userId, @PathVariable int itemId) {
		cartService.deleteCartItem(userId, itemId);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Checks out the cart and creates a purchase order.
	 *
	 * @param userId   the ID of the user
	 * @param username the username of the user
	 * @param address  the address for the order
	 * @return ResponseEntity containing the created purchase order
	 */
	@PostMapping("/checkout/{userId}")
	public ResponseEntity<PurchaseOrder> checkout(@PathVariable int userId, @RequestParam @Valid String username,
			@RequestParam String address) {
		PurchaseOrder purchaseOrder = cartService.checkout(userId, username, address);
		return ResponseEntity.ok(purchaseOrder);
	}

	/**
	 * Retrieves the order history for a specific user.
	 *
	 * @param userId the ID of the user
	 * @return ResponseEntity containing the list of purchase orders for the user
	 */
	@GetMapping("/orderHistory/{userId}")
	public ResponseEntity<List<PurchaseOrder>> getOrderHistory(@PathVariable int userId) {
		List<PurchaseOrder> orders = cartService.getOrderHistory(userId);
		return ResponseEntity.ok(orders);
	}

	/**
	 * Retrieves all orders.
	 *
	 * @return ResponseEntity containing the list of all purchase orders
	 */
	@GetMapping("/allOrders")
	public ResponseEntity<List<PurchaseOrder>> getAllOrders() {
		List<PurchaseOrder> orders = cartService.getAllOrders();
		return ResponseEntity.ok(orders);
	}

	/**
	 * Calculates the total price of all items in the cart for a specific user.
	 *
	 * @param userId the ID of the user
	 * @return ResponseEntity containing the total price
	 */
	@GetMapping("/total/{userId}")
	public ResponseEntity<Double> getTotalPrice(@PathVariable int userId) {
		double totalValue = cartService.calculateTotalValue(userId);
		return ResponseEntity.ok(totalValue);
	}
}