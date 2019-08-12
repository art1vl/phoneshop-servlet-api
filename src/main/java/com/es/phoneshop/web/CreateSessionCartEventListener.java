package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class CreateSessionCartEventListener implements HttpSessionListener {
    private static final String CART_SESSION_ATTRIBUTE = "cart";

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();

        Cart cart = new Cart();
        session.setAttribute(CART_SESSION_ATTRIBUTE, cart);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }
}
