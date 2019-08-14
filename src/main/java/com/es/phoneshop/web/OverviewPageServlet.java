package com.es.phoneshop.web;

import com.es.phoneshop.service.order.OrderService;
import com.es.phoneshop.service.order.OrderServiceImpl;
import com.es.phoneshop.exception.OrderNotFoundException;
import com.es.phoneshop.model.order.Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OverviewPageServlet extends HttpServlet {
    private OrderService orderService;

    @Override
    public void init() {
        orderService = OrderServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String secureId = request.getPathInfo().substring(1);

        try {
            Order order = orderService.getOrderBySecureId(secureId);
            request.setAttribute("order", order);
            request.getRequestDispatcher("/WEB-INF/pages/overview.jsp")
                    .forward(request, response);
        }
        catch (OrderNotFoundException exception) {
            request.setAttribute("exception", exception.getMessage());
            request.setAttribute("doNotShowMiniCart", true);
            request.getRequestDispatcher("/WEB-INF/pages/orderNotFound.jsp")
                    .forward(request, response);
        }
    }
}
