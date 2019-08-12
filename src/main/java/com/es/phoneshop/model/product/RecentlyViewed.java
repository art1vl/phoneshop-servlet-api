package com.es.phoneshop.model.product;

import javax.servlet.http.HttpServletRequest;
import java.util.Deque;

public interface RecentlyViewed {
    Deque getQueue(HttpServletRequest request);
    void refreshList(HttpServletRequest request, Product product);
}
