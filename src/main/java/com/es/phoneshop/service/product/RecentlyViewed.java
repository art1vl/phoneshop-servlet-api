package com.es.phoneshop.service.product;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.Deque;

public interface RecentlyViewed {
    Deque getQueue(HttpServletRequest request);
    void refreshList(HttpServletRequest request, Product product);
}
