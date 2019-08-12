package com.es.phoneshop.service.order;

import com.es.phoneshop.exception.OrderNotFoundException;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;

import javax.servlet.http.HttpServletRequest;

public interface OrderService {
    Order createOrder(Cart cart);
    void placeOrder(Order order);
    boolean checkForValidAndSetTheOrderIfOkay(HttpServletRequest request, Order order) throws OutOfStockException;
    Order getOrderBySecureId (String secureId) throws OrderNotFoundException;
}
