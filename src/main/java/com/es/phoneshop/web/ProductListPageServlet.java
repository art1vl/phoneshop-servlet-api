package com.es.phoneshop.web;

import com.es.phoneshop.service.product.DefaultProductService;
import com.es.phoneshop.service.product.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init(){
        productService = DefaultProductService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        String query = request.getParameter("query");
        request.setAttribute("products", productService.findAndSortProducts(sort, order, query));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
