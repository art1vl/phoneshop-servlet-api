package com.es.phoneshop.web;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.service.cart.CartService;
import com.es.phoneshop.service.cart.HttpSessionCartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartPageServlet extends HttpServlet {
    private final String ERRORS = "errors";
    private CartService cartService;


    @Override
    public void init(){
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);

        request.setAttribute("cart", cart);
        request.setAttribute("doNotShowMiniCart", true);

        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp")
                .forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] productIds = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");

        boolean hasError = false;
        String[] errors = new String[quantities.length];
        for (int i = 0; i < productIds.length; i++) {
            int quantity;
            try {
                quantity = Integer.valueOf(quantities[i]);
            } catch (NumberFormatException nfe) {
                errors[i] = "Not a number";
                hasError = true;
                continue;
            }
            try {
                cartService.update(request, Long.valueOf(productIds[i]), quantity);
            } catch (OutOfStockException exception) {
                errors[i] = exception.getMessage();
                hasError = true;
            }
        }

        if (hasError) {
            request.setAttribute(ERRORS, errors);
            doGet(request, response);
        }
        else {
            response.sendRedirect(request.getRequestURI() + "?message=Updated successfully");
        }
    }
}
