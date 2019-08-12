package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.HttpSessionProductService;
import com.es.phoneshop.model.product.HttpSessionRecentlyViewedService;
import com.es.phoneshop.model.product.ProductService;
import com.es.phoneshop.model.product.RecentlyViewed;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Deque;

public class ProductListPageServlet extends HttpServlet {
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
        request.setAttribute("recentlyViewed", deque);
        request.setAttribute("totalCost", cart.getTotalCost());
        request.setAttribute("totalQuantity", cart.getTotalQuantity());
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        String query = request.getParameter("query");
        request.setAttribute("products", productService.findAndSortProducts(sort, order, query));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
