package com.es.phoneshop.service.order;

import com.es.phoneshop.service.product.StockService;
import com.es.phoneshop.service.product.StockServiceImpl;
import com.es.phoneshop.dao.interfaces.OrderDao;
import com.es.phoneshop.dao.impl.OrderDaoImpl;
import com.es.phoneshop.exception.OrderNotFoundException;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {
    private static OrderServiceImpl instance;

    private OrderDao orderDao;
    private StockService stockService;

    private OrderServiceImpl() {
        orderDao = OrderDaoImpl.getInstance();
        stockService = StockServiceImpl.getInstance();
    }

    synchronized public static OrderServiceImpl getInstance() {
        if (instance == null) {
            instance = new OrderServiceImpl();
        }
        return instance;
    }

    @Override
    public Order createOrder(Cart cart) {
        if (cart == null) {
            throw new IllegalArgumentException("Cart can`t be null");
        }
        Order order = new Order();
        order.setCartItems(Collections.unmodifiableList(new ArrayList<>(cart.getCartItems())));
        order.setSubCost(cart.getSubCost());
        order.setTotalQuantity(cart.getTotalQuantity());

        order.setDeliveryCost(new BigDecimal(10));
        order.setDeliveryDate(new Date());
        order.setDeliveryMode(DeliveryMode.COURIER);
        order.setOrderCost(order.getSubCost().add(order.getDeliveryCost()));
        order.setPaymentMethod(PaymentMethod.CASH);
        return order;
    }

    @Override
    public void placeOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order can`t be null");
        }
        stockService.changeProductsStock(order.getCartItems());
        order.setSecureId(UUID.randomUUID().toString());
        orderDao.save(order);
    }

    public boolean checkForValidAndSetTheOrderIfOkay(HttpServletRequest request, Order order) throws OutOfStockException {
        boolean exceptionFlag = false;

        String name = request.getParameter("name");
        if ((name == null)
                || (name.equals(""))
                || (!name.matches("^[a-zA-Z]*$"))) {
            exceptionFlag = true;
            request.setAttribute("errorName", "Incorrect name");
        }
        else {
            order.setName(name);
        }

        String surname = request.getParameter("surname");
        if ((surname == null)
                || (surname.equals(""))
                || (!surname.matches("^[a-zA-Z]*$"))) {
            exceptionFlag = true;
            request.setAttribute("errorSurname", "Incorrect surname");
        }
        else {
            order.setSurname(surname);
        }

        String phone = request.getParameter("phone");
        if ((phone == null)
                || (phone.equals(""))
                || (!phone.matches("^\\+[0-9]+$"))) {
            exceptionFlag = true;
            request.setAttribute("errorPhone", "Incorrect phone number");
        }
        else {
            order.setPhone(phone);
        }

        String address = request.getParameter("address");
        if ((address == null)
                || (address.equals(""))) {
            exceptionFlag = true;
            request.setAttribute("errorAddress", "Incorrect address");
        }
        else {
            order.setAddress(address);
        }

        String deliveryMode = request.getParameter("deliveryMode");
        order.setDeliveryMode(DeliveryMode.valueOf(deliveryMode.toUpperCase().replace(" ", "")));

        String paymentMethod = request.getParameter("paymentMethod");
        order.setPaymentMethod(PaymentMethod.valueOf(paymentMethod.toUpperCase().replace(" ", "")));

        String date = request.getParameter("date");
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        try {
            order.setDeliveryDate(format.parse(date));
        }
        catch(ParseException exception) {
            throw new IllegalArgumentException("Incorrect date");
        }
        return exceptionFlag;
    }

    @Override
    public Order getOrderBySecureId(String secureId) throws OrderNotFoundException {
        return orderDao.getOrderBySecureId(secureId);
    }
}
