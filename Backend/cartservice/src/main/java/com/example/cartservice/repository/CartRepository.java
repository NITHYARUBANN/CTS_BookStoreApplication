package com.example.cartservice.repository;

import com.example.cartservice.entity.Cart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The CartRepository interface provides the mechanism for storage, retrieval,
 * update, and delete operations on Cart entities. This interface extends
 * JpaRepository which provides JPA related methods for standard data access
 * operations.
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    void deleteByUserIdAndId(int userId, int id);
	List<Cart> findAllByUserId(int userId);
	void deleteAllByUserId(int userId);
}