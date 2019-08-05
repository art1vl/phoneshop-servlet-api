package com.es.phoneshop.model.product;

import java.util.List;

public interface DaoService {
    List<Product> handler (String sort, String order, String query);
    List<Product> productsSorting (String sort, String order, List<Product> cloneProducts);
    List<Product> findProductsAccordingQuery (String query, List<Product> cloneProducts);
}
