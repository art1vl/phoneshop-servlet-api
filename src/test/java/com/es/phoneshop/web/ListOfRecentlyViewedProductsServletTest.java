//package com.es.phoneshop.web;
//
//import com.es.phoneshop.model.cart.Cart;
//import com.es.phoneshop.model.product.PriceHistory;
//import com.es.phoneshop.model.product.Product;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayDeque;
//import java.util.ArrayList;
//import java.util.Currency;
//import java.util.Deque;
//
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//import static org.mockito.Mockito.verify;
//
//@RunWith(MockitoJUnitRunner.class)
//public class ListOfRecentlyViewedProductsServletTest {
//    @Mock
//    private HttpServletRequest request;
//    @Mock
//    private HttpServletResponse response;
//    @Mock
//    private RequestDispatcher requestDispatcher;
//
//    private ListOfRecentlyViewedProductsServlet servlet;
//    private HttpSession httpSession;
//
//    private ArrayList<PriceHistory> startPriceHistory(BigDecimal price) {
//        ArrayList<PriceHistory> list = new ArrayList<>();
//        Currency usd = Currency.getInstance("USD");
//        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
//        try {
//            list.add(new PriceHistory(format.parse("1.01.2019"), new BigDecimal(200), usd));
//            list.add(new PriceHistory(format.parse("20.03.2019"), new BigDecimal(250), usd));
//            list.add(new PriceHistory(format.parse("23.06.2019"), price, usd));
//        }
//        catch (ParseException pe) {
//
//        }
//        return list;
//    }
//
//    @Before
//    public void setup(){
//        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
//        servlet = new ListOfRecentlyViewedProductsServlet();
//        servlet.init();
//    }
//
//    @Test
//    public void testDoGet() throws ServletException, IOException {
//        Deque deque = new ArrayDeque();
////        Product testProduct = new Product(10000L, "sgs", "ZZZZ", new BigDecimal(100), null, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", startPriceHistory(new BigDecimal(2000)));
////        deque.addFirst(testProduct);
//        httpSession = mock(HttpSession.class);
//        when(request.getSession()).thenReturn(httpSession);
//        when(httpSession.getAttribute(eq("recentlyViewed"))).thenReturn(deque);
//
//        servlet.doGet(request, response);
//
//        verify(request).setAttribute(eq("theListOfRecentlyViewedProducts"), eq(deque));
//        verify(requestDispatcher).include(request, response);
//    }
//}
