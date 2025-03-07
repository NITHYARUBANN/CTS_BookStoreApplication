package com.example.cartservice.service;

import com.example.cartservice.entity.Cart;
import com.example.cartservice.entity.PurchaseOrder;
import java.util.List;

public interface CartService {
    List<Cart> getAllItems(int userId);
    Cart getItemById(int id);
    Cart addBookToCart(int bookId, int quantity,int userId);
    void deleteCartItem(int userid, int itemId);
    double calculateTotalValue(int userId);
    PurchaseOrder checkout(int userId, String username, String address);
    List<PurchaseOrder> getOrderHistory(int userId);
    List<PurchaseOrder> getAllOrders();
}
