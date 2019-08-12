package com.es.phoneshop.service.product;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.cart.CartItem;

import java.util.List;

public interface StockService {
    void changeProductsStock(List<CartItem> cartItems) throws OutOfStockException;
}
