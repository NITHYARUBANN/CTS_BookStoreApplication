package com.example.cartservice;

import com.example.cartservice.dto.BookDto;
import com.example.cartservice.entity.Cart;
import com.example.cartservice.entity.PurchaseOrder;
import com.example.cartservice.exception.InsufficientStockException;
import com.example.cartservice.exception.ResourceNotFoundException;
import com.example.cartservice.repository.CartRepository;
import com.example.cartservice.repository.PurchaseOrderRepository;
import com.example.cartservice.service.CartServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private PurchaseOrderRepository purchaseOrderRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CartServiceImpl cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllItems() {
        Cart cart = new Cart();
        cart.setUserId(1);
        when(cartRepository.findAllByUserId(anyInt())).thenReturn(Arrays.asList(cart));

        List<Cart> items = cartService.getAllItems(1);
        assertEquals(1, items.size());
        verify(cartRepository, times(1)).findAllByUserId(anyInt());
    }

    @Test
    void testGetItemById() {
        Cart cart = new Cart();
        cart.setId(1);
        when(cartRepository.findById(anyInt())).thenReturn(Optional.of(cart));

        Cart item = cartService.getItemById(1);
        assertNotNull(item);
        assertEquals(1, item.getId());
        verify(cartRepository, times(1)).findById(anyInt());
    }

    @Test
    void testAddBookToCart() {
        BookDto bookDto = new BookDto();
        bookDto.setId(1);
        bookDto.setTitle("Test Book");
        bookDto.setQuantity(10);
        bookDto.setPrice(100.0);
        ResponseEntity<BookDto> responseEntity = new ResponseEntity<>(bookDto, HttpStatus.OK);

        when(restTemplate.getForEntity(anyString(), eq(BookDto.class))).thenReturn(responseEntity);
        when(cartRepository.save(any(Cart.class))).thenReturn(new Cart());

        Cart cart = cartService.addBookToCart(1, 2, 1);
        assertNotNull(cart);
        verify(restTemplate, times(1)).getForEntity(anyString(), eq(BookDto.class));
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void testAddBookToCart_InsufficientStock() {
        BookDto bookDto = new BookDto();
        bookDto.setId(1);
        bookDto.setTitle("Test Book");
        bookDto.setQuantity(1);
        ResponseEntity<BookDto> responseEntity = new ResponseEntity<>(bookDto, HttpStatus.OK);

        when(restTemplate.getForEntity(anyString(), eq(BookDto.class))).thenReturn(responseEntity);

        assertThrows(InsufficientStockException.class, () -> {
            cartService.addBookToCart(1, 2, 1);
        });
        verify(restTemplate, times(1)).getForEntity(anyString(), eq(BookDto.class));
    }

    @Test
    void testAddBookToCart_BookNotFound() {
        ResponseEntity<BookDto> responseEntity = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        when(restTemplate.getForEntity(anyString(), eq(BookDto.class))).thenReturn(responseEntity);

        assertThrows(ResourceNotFoundException.class, () -> {
            cartService.addBookToCart(1, 2, 1);
        });
        verify(restTemplate, times(1)).getForEntity(anyString(), eq(BookDto.class));
    }

    @Test
    void testDeleteCartItem() {
        doNothing().when(cartRepository).deleteByUserIdAndId(anyInt(), anyInt());

        cartService.deleteCartItem(1, 1);
        verify(cartRepository, times(1)).deleteByUserIdAndId(anyInt(), anyInt());
    }

    @Test
    void testCalculateTotalValue() {
        Cart cart = new Cart();
        cart.setPrice(100.0);
        cart.setQuantity(2);
        when(cartRepository.findAllByUserId(anyInt())).thenReturn(Arrays.asList(cart));

        double totalValue = cartService.calculateTotalValue(1);
        assertEquals(200.0, totalValue);
        verify(cartRepository, times(1)).findAllByUserId(anyInt());
    }

    @Test
    void testCheckout() {
        Cart cart = new Cart();
        cart.setName("Test Book");
        cart.setQuantity(2);
        cart.setPrice(100.0);
        cart.setUserId(1);

        when(cartRepository.findAllByUserId(anyInt())).thenReturn(Arrays.asList(cart));
        when(purchaseOrderRepository.save(any(PurchaseOrder.class))).thenAnswer(invocation -> invocation.getArgument(0));
        doNothing().when(cartRepository).deleteAllByUserId(anyInt());

        PurchaseOrder order = cartService.checkout(1, "testuser", "testaddress");

        assertNotNull(order);
        assertEquals(1, order.getUserId());
        assertEquals("testuser", order.getUsername());
        assertEquals("Test Book (2), ", order.getOrderDetails());
        assertEquals(200.0, order.getTotalCost());
        assertEquals("testaddress", order.getAddress());

        verify(cartRepository, times(2)).findAllByUserId(anyInt()); // Expecting 2 invocations
        verify(purchaseOrderRepository, times(1)).save(any(PurchaseOrder.class));
        verify(cartRepository, times(1)).deleteAllByUserId(anyInt());
    }
    @Test
    void testGetOrderHistory() {
        PurchaseOrder order = new PurchaseOrder();
        order.setUserId(1);
        when(purchaseOrderRepository.findByUserId(anyInt())).thenReturn(Arrays.asList(order));

        List<PurchaseOrder> orders = cartService.getOrderHistory(1);
        assertEquals(1, orders.size());
        verify(purchaseOrderRepository, times(1)).findByUserId(anyInt());
    }

    @Test
    void testGetOrderHistory_NoOrders() {
        when(purchaseOrderRepository.findByUserId(anyInt())).thenReturn(Arrays.asList());

        assertThrows(ResourceNotFoundException.class, () -> {
            cartService.getOrderHistory(1);
        });
        verify(purchaseOrderRepository, times(1)).findByUserId(anyInt());
    }

    @Test
    void testGetAllOrders() {
        PurchaseOrder order = new PurchaseOrder();
        when(purchaseOrderRepository.findAll()).thenReturn(Arrays.asList(order));

        List<PurchaseOrder> orders = cartService.getAllOrders();
        assertEquals(1, orders.size());
        verify(purchaseOrderRepository, times(1)).findAll();
    }
}