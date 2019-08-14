package com.es.phoneshop.service.product;

import com.es.phoneshop.model.product.AdvancedSearchBean;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AdvancedSearchService {
    public List<Product> finalResult(HttpServletRequest request, AdvancedSearchBean query);
    public boolean checkDate(HttpServletRequest request, AdvancedSearchBean query);
}
