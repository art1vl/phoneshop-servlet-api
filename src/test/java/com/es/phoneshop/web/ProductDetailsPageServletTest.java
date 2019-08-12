package com.es.phoneshop.web;

import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.product.PriceHistory;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.product.DefaultProductService;
import com.es.phoneshop.service.product.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ProductService productService;

    private ProductDetailsPageServlet servlet;
    private ProductService productServ;

    private ArrayList<PriceHistory> startPriceHistory(BigDecimal price) {
        ArrayList<PriceHistory> list = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        try {
            list.add(new PriceHistory(format.parse("1.01.2019"), new BigDecimal(200), usd));
            list.add(new PriceHistory(format.parse("20.03.2019"), new BigDecimal(250), usd));
            list.add(new PriceHistory(format.parse("23.06.2019"), price, usd));
        }
        catch (ParseException pe) {

        }
        return list;
    }

    @Before
    public void setup(){
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        productServ = DefaultProductService.getInstance();
        servlet = new ProductDetailsPageServlet();
        servlet.init();
    }

    @Test
    public void testDoGetForNumberFormatException() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("gfd");

        servlet.doGet(request, response);

        verify(request).setAttribute(eq("id"), anyString());
        verify(request).setAttribute(eq("doNotShowMiniCart"), anyBoolean());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetForProductNotFoundException() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/99");
        Long variable = 99L;

        servlet.doGet(request, response);

        verify(request).setAttribute(eq("id"), eq(variable));
        verify(request).setAttribute(eq("doNotShowMiniCart"), anyBoolean());
        verify(requestDispatcher).forward(request, response);
    }

//    @Test
//    public void testDoGetForNormalWork() throws ServletException, IOException {
//        Product testProduct = new Product(10000L, "sgs", "ZZZZ", new BigDecimal(100), null, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", startPriceHistory(new BigDecimal(2000)));
//        productServ.save(testProduct);
//        when(request.getPathInfo()).thenReturn("/10000");
//        Long variable = 10000L;
//        when(productServ.getProduct(eq(variable))).thenReturn(testProduct);
//
//        servlet.doGet(request, response);
//
//        verify(request).setAttribute(eq("product"), eq(testProduct));
//        verify(requestDispatcher).forward(request, response);
//    }
}
