package com.es.phoneshop.service.cart;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.cart.Cart;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    Cart getCart(HttpServletRequest request);
    void add(HttpServletRequest request, Long id, int quantity) throws OutOfStockException, ProductNotFoundException;
    void update(HttpServletRequest request, Long id, int quantity) throws OutOfStockException, ProductNotFoundException;
    void deleteCartItem(HttpServletRequest request, Long id) throws ProductNotFoundException;
    void cleanCart(Cart cart);
}
