package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayDeque;
import java.util.Deque;

public class CreatingCartAndRecentlyViewedWhenSessionCreatedListener implements HttpSessionListener {
    private static final String CART_SESSION_ATTRIBUTE = "cart";
    private static final String RECENTLY_VIEWED_SESSION_ATTRIBUTE = "recentlyViewed";

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();

        Cart cart = new Cart();
        session.setAttribute(CART_SESSION_ATTRIBUTE, cart);

        Deque<Product> recentlyViewed = new ArrayDeque<>();
        session.setAttribute(RECENTLY_VIEWED_SESSION_ATTRIBUTE, recentlyViewed);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }
}
