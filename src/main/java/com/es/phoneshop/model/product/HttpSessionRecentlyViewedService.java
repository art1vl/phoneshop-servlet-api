package com.es.phoneshop.model.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayDeque;
import java.util.Deque;

public class HttpSessionRecentlyViewedService implements RecentlyViewed {
    private static final String ATTRIBUTE = "recentlyViewed";

    private static HttpSessionRecentlyViewedService instance;

    private HttpSessionRecentlyViewedService() {}

    synchronized public static HttpSessionRecentlyViewedService getInstance() {
        if (instance == null){
            instance = new HttpSessionRecentlyViewedService();
        }
        return instance;
    }

    @Override
    public Deque getQueue(HttpServletRequest request) {
        Deque<Product>  result = (Deque<Product>) request.getSession().getAttribute(ATTRIBUTE);
        return new ArrayDeque(result);
    }

    @Override
    public void refreshList(HttpServletRequest request, Product product) {
        HttpSession session = request.getSession();
        synchronized (session) {
            Deque<Product> queue = (Deque<Product>) session.getAttribute(ATTRIBUTE);
            queue.remove(product);
            int maxAmountInTheQueue = 3;
            if (queue.size() < maxAmountInTheQueue) {
                queue.addFirst(product);
            } else {
                queue.pollLast();
                queue.addFirst(product);
            }
        }
    }
}
