package com.es.phoneshop.web;

import com.es.phoneshop.service.product.HttpSessionRecentlyViewedService;
import com.es.phoneshop.service.product.RecentlyViewed;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Deque;

public class ListOfRecentlyViewedProductsServlet extends HttpServlet {
    private RecentlyViewed recentlyViewedService;

    @Override
    public void init() {
        recentlyViewedService = HttpSessionRecentlyViewedService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Deque deque = recentlyViewedService.getQueue(request);

        request.setAttribute("theListOfRecentlyViewedProducts", deque);

        request.getRequestDispatcher("/WEB-INF/common/listOfRecentlyViewedProducts.jsp").include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Deque deque = recentlyViewedService.getQueue(request);

        request.setAttribute("theListOfRecentlyViewedProducts", deque);

        request.getRequestDispatcher("/WEB-INF/common/listOfRecentlyViewedProducts.jsp").include(request, response);
    }
}
