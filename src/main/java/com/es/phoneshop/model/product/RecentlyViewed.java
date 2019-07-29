package com.es.phoneshop.model.product;

import javax.servlet.http.HttpServletRequest;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

public interface RecentlyViewed {
    Deque getQueue(HttpServletRequest request);
    Deque refreshList(Deque queue, Product product);
}
