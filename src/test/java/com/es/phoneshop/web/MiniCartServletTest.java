package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MiniCartServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;

    private MiniCartServlet servlet;
    private HttpSession httpSession;

    @Before
    public void setup(){
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        servlet = new MiniCartServlet();
        servlet.init();
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        Cart cart = new Cart();
        httpSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute(eq("cart"))).thenReturn(cart);

        servlet.doGet(request, response);

        verify(request).setAttribute(eq("cart"), eq(cart));
        verify(requestDispatcher).include(request, response);
    }
}
