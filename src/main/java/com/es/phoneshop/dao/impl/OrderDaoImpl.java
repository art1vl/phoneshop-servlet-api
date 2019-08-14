package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.interfaces.OrderDao;
import com.es.phoneshop.exception.OrderNotFoundException;
import com.es.phoneshop.model.order.Order;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OrderDaoImpl implements OrderDao {
    private static OrderDaoImpl instance;

    private Long id;
    private List<Order> orders;

    private OrderDaoImpl() {
        orders = new CopyOnWriteArrayList<>();
        id = 0L;
    }

    synchronized public static OrderDaoImpl getInstance() {
        if (instance == null){
            instance = new OrderDaoImpl();
        }
        return instance;
    }

    @Override
    synchronized public void save(Order order) {
        order.setId(++id);
        orders.add(order);
    }

    @Override
    public Order getOrderBySecureId(String id) throws OrderNotFoundException{
        if (id == null) {
            throw new NullPointerException("id should not be null");
        }
        return orders.stream()
                     .filter(o -> o.getSecureId().equals(id))
                     .findAny()
                     .orElseThrow(() -> new OrderNotFoundException(id, "There is no such order")).cloneOrder();
    }
}
