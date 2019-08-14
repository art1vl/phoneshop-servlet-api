package com.es.phoneshop.dao.interfaces;

import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;

import java.util.Comparator;
import java.util.List;

public interface ProductDao {
    Product getProduct(Long id) throws ProductNotFoundException;
    void changeProductStock(Long id, int newStock);
    List<Product> findProducts();
    void save(Product product);
    void delete(Long id);
    void cleanListOfProducts();
    List<Product> productsSorting (Comparator<Product> comparator, List<Product> cloneProducts);
}
