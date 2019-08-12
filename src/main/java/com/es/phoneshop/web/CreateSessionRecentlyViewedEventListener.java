package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayDeque;
import java.util.Deque;

public class CreateSessionRecentlyViewedEventListener implements HttpSessionListener {
    private static final String RECENTLY_VIEWED_SESSION_ATTRIBUTE = "recentlyViewed";

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();

        Deque<Product> recentlyViewed = new ArrayDeque<>();
        session.setAttribute(RECENTLY_VIEWED_SESSION_ATTRIBUTE, recentlyViewed);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }
}
