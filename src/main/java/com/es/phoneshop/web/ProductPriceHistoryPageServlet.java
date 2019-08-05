package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.HttpSessionProductService;
import com.es.phoneshop.model.product.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductPriceHistoryPageServlet extends HttpServlet {
    private ProductService productService;
    private CartService cartService;

    @Override
    public void init(){
        productService = HttpSessionProductService.getInstance();
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        request.setAttribute("totalCost", cart.getTotalCost());
        request.setAttribute("totalQuantity", cart.getTotalQuantity());
        try {
            Long idFromPath = parseProductId(request);
            request.setAttribute("product", productService.getProduct(idFromPath));
            request.getRequestDispatcher("/WEB-INF/pages/productPriceHistory.jsp").forward(request, response);
        }
        catch (NumberFormatException nfe){
            request.setAttribute("id", request.getPathInfo().substring(1));
            request.getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp").forward(request, response);
        }
    }

    private Long parseProductId(HttpServletRequest request){
        return Long.valueOf(request.getPathInfo().substring(1));
    }
}
