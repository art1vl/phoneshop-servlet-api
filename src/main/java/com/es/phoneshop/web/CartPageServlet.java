package com.es.phoneshop.web;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Deque;

public class CartPageServlet extends HttpServlet {
    private final String ERROR = "errors";
    private ProductService productService;
    private CartService cartService;
    private RecentlyViewed recentlyViewedService;


    @Override
    public void init(){
        productService = HttpSessionProductService.getInstance();
        cartService = HttpSessionCartService.getInstance();
        recentlyViewedService = HttpSessionRecentlyViewedService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        Deque deque = recentlyViewedService.getQueue(request);
        try {
            request.setAttribute("recentlyViewed", deque);
            request.setAttribute("totalQuantity", cart.getTotalQuantity());
            request.setAttribute("cart", cart);
            request.setAttribute("flagDoNotShowBasket", true);
            request.getRequestDispatcher("/WEB-INF/pages/cart.jsp")
                    .forward(request, response);
        }
        catch (NumberFormatException nfe){
            request.setAttribute("id", request.getPathInfo().substring(1));
            request.setAttribute("flagForException", true);
            request.getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp")
                    .forward(request, response);
        }
        catch (ProductNotFoundException pnfe){
            request.setAttribute("id", parseProductId(request));
            request.setAttribute("flagForException", true);
            request.getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp")
                    .forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] productIds = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");

        boolean hasError = false;
        String[] errors = new String[quantities.length];
        for (int i = 0; i < productIds.length; i++) {
            Long quantity;
            try {
                quantity = Long.valueOf(quantities[i]);
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
                continue;
            }
        }

        if (hasError) {
            request.setAttribute(ERROR, errors);
            doGet(request, response);
        }
        else {
            response.sendRedirect(request.getRequestURI() + "?message=Updated successfully");
        }
    }

    private Long parseProductId(HttpServletRequest request) throws NumberFormatException{
        return Long.valueOf(request.getPathInfo().substring(1));
    }
}
