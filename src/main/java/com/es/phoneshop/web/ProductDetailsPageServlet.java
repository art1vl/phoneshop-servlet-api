package com.es.phoneshop.web;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.HttpSessionRecentlyViewedService;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Deque;
import java.util.Locale;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;
    private HttpSessionRecentlyViewedService recentlyViewedService;


    @Override
    public void init(){
        productDao = ArrayListProductDao.getInstance();
        cartService = HttpSessionCartService.getInstance();
        recentlyViewedService = HttpSessionRecentlyViewedService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        Deque deque = recentlyViewedService.getQueue(request);
        try {
            request.setAttribute("recentlyViewed", deque);
            recentlyViewedService.refreshList(deque, productDao.getProduct(parseProductId(request)));
            request.setAttribute("cart", cart.getProductQuantity());
            request.setAttribute("product", productDao.getProduct(parseProductId(request)));
            request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp")
                    .forward(request, response);
        }
        catch (NumberFormatException nfe){
            request.setAttribute("id", request.getPathInfo().substring(1));
            request.getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp")
                    .forward(request, response);
        }
        catch (ProductNotFoundException pnfe){
            request.setAttribute("id", parseProductId(request));
            request.getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp")
                    .forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        try {
            Locale locale = request.getLocale();
            Long quantity = new Long(NumberFormat.getInstance(locale).parse(request.getParameter("quantity")).longValue());
            Product product = productDao.getProduct(parseProductId(request));

            cartService.add(cart, product, quantity);

            response.sendRedirect(request.getContextPath() + request.getServletPath() + request.getPathInfo()
            + "?message=Added to cart successfully");
            return;
        }
        catch (ParseException pe){
            request.setAttribute("error", "Not a number");
        }
        catch (OutOfStockException ose) {
            request.setAttribute("error", "Out of Stock. Max stock is " + ose.getMaxStock());
        }
        doGet(request, response);
    }

    private Long parseProductId(HttpServletRequest request){
        return Long.valueOf(request.getPathInfo().substring(1));
    }
}
