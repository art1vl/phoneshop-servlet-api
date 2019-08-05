package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.HttpSessionCartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCartItemServlet extends HttpServlet {
    private HttpSessionCartService cartService;

    @Override
    public void init() {
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = getIdFromPath(request);

        cartService.deleteCartItem(request, productId);

        response.sendRedirect(request.getContextPath() + "/cart");
    }

    private Long getIdFromPath (HttpServletRequest request) {
        return Long.valueOf(request.getPathInfo().substring(1));
    }
}
