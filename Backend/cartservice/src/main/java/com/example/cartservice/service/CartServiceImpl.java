package com.example.cartservice.service;

import com.example.cartservice.dto.BookDto;
import com.example.cartservice.entity.Cart;
import com.example.cartservice.entity.PurchaseOrder;
import com.example.cartservice.exception.InsufficientStockException;
import com.example.cartservice.exception.ResourceNotFoundException;
import com.example.cartservice.repository.CartRepository;
import com.example.cartservice.repository.PurchaseOrderRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Service implementation for managing cart operations.
 */
@Service
public class CartServiceImpl implements CartService {

	private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

	private final CartRepository cartRepository;
	private final PurchaseOrderRepository purchaseOrderRepository;
	private final RestTemplate restTemplate;

	/**
	 * Constructor for CartServiceImpl.
	 *
	 * @param cartRepository          the cart repository
	 * @param purchaseOrderRepository the purchase order repository
	 * @param restTemplate            the RestTemplate for making HTTP requests
	 */
	public CartServiceImpl(CartRepository cartRepository, PurchaseOrderRepository purchaseOrderRepository,
			RestTemplate restTemplate) {
		this.cartRepository = cartRepository;
		this.purchaseOrderRepository = purchaseOrderRepository;
		this.restTemplate = restTemplate;
	}

	/**
	 * Fetches all items in the cart for a given user.
	 *
	 * @param userId the user ID
	 * @return a list of cart items
	 */
	@Override
	public List<Cart> getAllItems(int userId) {
		logger.info("Fetching all items in the cart for user ID: {}", userId);
		return cartRepository.findAllByUserId(userId);
	}

	/**
	 * Fetches a specific cart item by its ID.
	 *
	 * @param id the item ID
	 * @return the cart item, or null if not found
	 */
	@Override
	public Cart getItemById(int id) {
		logger.info("Fetching item with ID: {}", id);
		return cartRepository.findById(id).orElse(null);
	}

	/**
	 * Adds a book to the cart.
	 *
	 * @param bookId   the book ID
	 * @param quantity the quantity to add
	 * @param userId   the user ID
	 * @return the updated cart item
	 * @throws InsufficientStockException if there is not enough stock
	 * @throws ResourceNotFoundException  if the book is not found
	 */
	@Override
	public Cart addBookToCart(int bookId, int quantity, int userId) {
		logger.info("Adding book with ID: {} and quantity: {} to the cart", bookId, quantity);
		String bookServiceUrl = "http://localhost:8002/books/" + bookId;
		ResponseEntity<BookDto> response = restTemplate.getForEntity(bookServiceUrl, BookDto.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			BookDto book = response.getBody();
			if (book != null) {
				if (book.getQuantity() >= quantity) {
					Cart cart = new Cart();
					cart.setBookId(bookId);
					cart.setName(book.getTitle());
					cart.setQuantity(quantity);
					cart.setUserId(userId);
					cart.setPrice(book.getPrice());
					logger.info("Book with ID: {} added to the cart", bookId);
					return cartRepository.save(cart);
				} else {
					logger.warn("Insufficient stock for book with ID: {}", bookId);
					throw new InsufficientStockException("Insufficient Books at the moment, Out of Stock!");
				}
			}
		}
		logger.warn("Book with ID: {} not found", bookId);
		throw new ResourceNotFoundException("Book not found with ID: " + bookId);
	}

	/**
	 * Deletes a specific item from the cart.
	 *
	 * @param userId the user ID
	 * @param itemId the item ID
	 */
	@Override
	@Transactional
	public void deleteCartItem(int userId, int itemId) {
		cartRepository.deleteByUserIdAndId(userId, itemId);
	}

	/**
	 * Calculates the total value of the cart for a given user.
	 *
	 * @param userId the user ID
	 * @return the total value of the cart
	 */
	@Override
	@Transactional
	public double calculateTotalValue(int userId) {
		logger.info("Calculating total value of the cart for user ID: {}", userId);
		List<Cart> carts = cartRepository.findAllByUserId(userId);
		double totalValue = 0.0;
		for (Cart cart : carts) {
			totalValue += cart.getPrice() * cart.getQuantity();
		}
		logger.info("Total value of the cart for user ID {}: {}", userId, totalValue);
		return totalValue;
	}

	/**
	 * Checks out the cart for a given user, creating a purchase order and updating
	 * stock quantities.
	 *
	 * @param userId   the user ID
	 * @param username the username
	 * @param address  the shipping address
	 * @return the created purchase order
	 * @throws InsufficientStockException if there is not enough stock
	 */
	@Override
	@Transactional
	public PurchaseOrder checkout(int userId, String username, String address) {
		logger.info("Checking out cart for user ID: {}", userId);
		List<Cart> carts = cartRepository.findAllByUserId(userId);
		double totalValue = calculateTotalValue(userId);
		StringBuilder orderDetails = new StringBuilder();

		for (Cart cart : carts) {
			orderDetails.append(cart.getName()).append(" (").append(cart.getQuantity()).append("), ");

			// Update the book quantity in the database
			String bookServiceUrl = "http://localhost:8002/books/" + cart.getBookId();
			ResponseEntity<BookDto> response = restTemplate.getForEntity(bookServiceUrl, BookDto.class);
			if (response.getStatusCode() == HttpStatus.OK) {
				BookDto book = response.getBody();
				if (book != null) {
					if (book.getQuantity() >= cart.getQuantity()) {
						String updateBookUrl = "http://localhost:8002/books/edit/" + cart.getBookId();
						book.setQuantity(book.getQuantity() - cart.getQuantity());
						restTemplate.put(updateBookUrl, book);
					} else {
						logger.warn("Insufficient stock for book with ID: {}", cart.getBookId());
						throw new InsufficientStockException("Insufficient Books at the moment, Out of Stock!");
					}
				}
			}
		}

		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setUserId(userId);
		purchaseOrder.setUsername(username);
		purchaseOrder.setOrderDetails(orderDetails.toString());
		purchaseOrder.setTotalCost(totalValue);
		purchaseOrder.setAddress(address);

		purchaseOrderRepository.save(purchaseOrder);
		cartRepository.deleteAllByUserId(userId);

		logger.info("Purchase order created for user ID: {}", userId);
		return purchaseOrder;
	}

	/**
	 * Fetches the order history for a given user.
	 *
	 * @param userId the user ID
	 * @return a list of purchase orders
	 * @throws ResourceNotFoundException if no orders are found for the user
	 */
	@Override
	public List<PurchaseOrder> getOrderHistory(int userId) {
		logger.info("Fetching order history for user ID: {}", userId);
		List<PurchaseOrder> orders = purchaseOrderRepository.findByUserId(userId);
		if (orders.isEmpty()) {
			throw new ResourceNotFoundException("No user was found with the user ID: " + userId);
		}
		return orders;
	}

	/**
	 * Fetches all purchase orders.
	 *
	 * @return a list of all purchase orders
	 */
	@Override
	public List<PurchaseOrder> getAllOrders() {
		logger.info("Fetching all orders");
		return purchaseOrderRepository.findAll();
	}
}