package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.HttpSessionDaoService;
import com.es.phoneshop.model.product.HttpSessionRecentlyViewedService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Deque;

public class ProductListPageServlet extends HttpServlet {
    private HttpSessionDaoService daoService;
    private CartService cartService;
    private HttpSessionRecentlyViewedService recentlyViewedService;

    @Override
    public void init(){
        daoService = HttpSessionDaoService.getInstance();
        cartService = HttpSessionCartService.getInstance();
        recentlyViewedService = HttpSessionRecentlyViewedService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        Deque deque = recentlyViewedService.getQueue(request);
        request.setAttribute("recentlyViewed", deque);
        request.setAttribute("cart", cart.getProductQuantity());
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        String query = request.getParameter("query");
        request.setAttribute("products", daoService.handler(sort, order, query));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
