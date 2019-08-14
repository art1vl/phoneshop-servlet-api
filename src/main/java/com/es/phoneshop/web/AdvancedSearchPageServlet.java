package com.es.phoneshop.web;

import com.es.phoneshop.model.product.AdvancedSearchBean;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.product.AdvancedSearchService;
import com.es.phoneshop.service.product.AdvancedSearchServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdvancedSearchPageServlet extends HttpServlet {
    private AdvancedSearchService advancedSearchService;

    @Override
    public void init() {
        advancedSearchService = AdvancedSearchServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/advancedSearchPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        AdvancedSearchBean query = new AdvancedSearchBean();
        boolean exceptionFlag = advancedSearchService.checkDate(request, query);
        if (exceptionFlag) {
            request.setAttribute("exception", true);
            doGet(request, response);
        }
        else {
            List<Product> list = advancedSearchService.finalResult(request, query);
            request.setAttribute("list", list);
            doGet(request, response);
        }
    }
}
