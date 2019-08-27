package com.es.phoneshop.web;

import com.es.phoneshop.service.product.DefaultProductService;
import com.es.phoneshop.service.product.HttpSessionRecentlyViewedService;
import com.es.phoneshop.service.product.ProductService;
import com.es.phoneshop.service.product.RecentlyViewed;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.service.cart.CartService;
import com.es.phoneshop.service.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductService productService;
    private CartService cartService;
    private RecentlyViewed recentlyViewedService;


    @Override
    public void init(){
        productService = DefaultProductService.getInstance();
        cartService = HttpSessionCartService.getInstance();
        recentlyViewedService = HttpSessionRecentlyViewedService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            recentlyViewedService.refreshList(request, productService.getProduct(parseProductId(request)));
            request.setAttribute("product", productService.getProduct(parseProductId(request)));
            request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp")
                    .forward(request, response);
            
        }
        catch (NumberFormatException nfe){
            request.setAttribute("id", request.getPathInfo().substring(1));
            request.setAttribute("doNotShowMiniCart", true);
            request.getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp")
                    .forward(request, response);
        }
        catch (ProductNotFoundException pnfe){
            request.setAttribute("id", parseProductId(request));
            request.setAttribute("doNotShowMiniCart", true);
            request.getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp")
                    .forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Locale locale = request.getLocale();
            int quantity = NumberFormat.getInstance(locale).parse(request.getParameter("quantity")).intValue();
            Product product = productService.getProduct(parseProductId(request));

            cartService.add(request, product.getId(), quantity);

            response.sendRedirect(request.getContextPath() + request.getServletPath() + request.getPathInfo()
            + "?message=Added to cart successfully");
            return;
        }
        catch (ParseException pe){
            request.setAttribute("error", "Not a number");
        }
        catch (OutOfStockException ose) {
            request.setAttribute("error", ose.getMessage());
        }
        doGet(request, response);
    }

    protected Long parseProductId(HttpServletRequest request) throws NumberFormatException{
        return Long.valueOf(request.getPathInfo().substring(1));
    }
}
