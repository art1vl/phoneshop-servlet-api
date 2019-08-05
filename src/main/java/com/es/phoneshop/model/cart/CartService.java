package com.es.phoneshop.model.cart;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.exception.ProductNotFoundException;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    Cart getCart(HttpServletRequest request);
    void add(HttpServletRequest request, Long id, Long quantity) throws OutOfStockException, ProductNotFoundException;
    void update(HttpServletRequest request, Long id, Long quantity) throws OutOfStockException, ProductNotFoundException;
    void deleteCartItem(HttpServletRequest request, Long id) throws ProductNotFoundException;
}
