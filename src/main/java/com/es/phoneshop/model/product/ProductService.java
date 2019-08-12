package com.es.phoneshop.model.product;

import com.es.phoneshop.exception.ProductNotFoundException;

import java.util.List;

public interface ProductService {
    List<Product> findAndSortProducts(String sort, String order, String query) throws ProductNotFoundException;
    List<Product> productsSorting (String sort, String order, List<Product> cloneProducts);
    List<Product> findProductsAccordingQuery (String query, List<Product> cloneProducts);
    Product getProduct(Long id) throws ProductNotFoundException;
    void save(Product product);
    void delete(Long id);
}
