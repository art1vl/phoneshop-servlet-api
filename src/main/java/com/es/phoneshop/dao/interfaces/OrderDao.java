package com.es.phoneshop.dao.interfaces;

import com.es.phoneshop.exception.OrderNotFoundException;
import com.es.phoneshop.model.order.Order;

public interface OrderDao {
    void save(Order order);
    Order getOrderBySecureId (String id) throws OrderNotFoundException;
}
