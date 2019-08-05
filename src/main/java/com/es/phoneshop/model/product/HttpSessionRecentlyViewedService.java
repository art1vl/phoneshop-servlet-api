package com.es.phoneshop.model.product;

import javax.servlet.http.HttpServletRequest;
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
        if (result == null){
            result = new ArrayDeque<>();
            request.getSession().setAttribute(ATTRIBUTE, result);
        }
        return result;
    }

    @Override
    public Deque refreshList(Deque queue, Product product) {
        queue.remove(product);
        if (queue.size() < 3){
            queue.addFirst(product);
        }
        else{
            queue.pollLast();
            queue.addFirst(product);
        }
        return queue;
    }
}
