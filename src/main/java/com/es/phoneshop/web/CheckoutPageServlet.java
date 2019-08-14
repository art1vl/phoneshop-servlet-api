package com.es.phoneshop.web;

import com.es.phoneshop.service.order.OrderService;
import com.es.phoneshop.service.order.OrderServiceImpl;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.service.cart.CartService;
import com.es.phoneshop.service.cart.HttpSessionCartService;
import com.es.phoneshop.model.order.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class CheckoutPageServlet extends HttpServlet {
    private CartService cartService;
    private OrderService orderService;

    @Override
    public void init() {
        cartService = HttpSessionCartService.getInstance();
        orderService = OrderServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        Order order = orderService.createOrder(cart);

        request.setAttribute("order", order);
        int oneDay = 24*3600*1000;
        request.setAttribute("date", new Date(order.getDeliveryDate().getTime() + oneDay));

        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Cart cart = cartService.getCart(request);
        Order order = orderService.createOrder(cart);

        boolean exceptionFlag;
        try {
            exceptionFlag = orderService.checkForValidAndSetTheOrderIfOkay(request, order);
        }
        catch (OutOfStockException exception) {
            request.setAttribute("outOfStockException", exception.getMessage());
            exceptionFlag = true;
        }
        if (exceptionFlag) {
            doGet(request, response);
        }
        else {
            orderService.placeOrder(order);
            cartService.cleanCart(cart);
            response.sendRedirect(request.getContextPath() + "/order/overview/" + order.getSecureId());
        }
    }
}
