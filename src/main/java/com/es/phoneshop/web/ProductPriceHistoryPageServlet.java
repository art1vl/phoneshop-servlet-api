package com.es.phoneshop.web;

import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.service.product.DefaultProductService;
import com.es.phoneshop.service.product.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductPriceHistoryPageServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() {
        productService = DefaultProductService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long idFromPath = parseProductId(request);
            request.setAttribute("product", productService.getProduct(idFromPath));
            request.getRequestDispatcher("/WEB-INF/pages/productPriceHistory.jsp").forward(request, response);
        }
        catch (NumberFormatException nfe){
            request.setAttribute("id", request.getPathInfo().substring(1));
            request.setAttribute("doNotShowMiniCart", true);
            request.getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp").forward(request, response);
        }
        catch (ProductNotFoundException pnfe){
            request.setAttribute("id", parseProductId(request));
            request.setAttribute("doNotShowMiniCart", true);
            request.getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp")
                    .forward(request, response);
        }
    }

    private Long parseProductId(HttpServletRequest request){
        return Long.valueOf(request.getPathInfo().substring(1));
    }
}
