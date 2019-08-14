package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.interfaces.ProductDao;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;

import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static ArrayListProductDao instance;

    private List<Product> products;

    private ArrayListProductDao() {
        products = new ArrayList<>();
    }

    synchronized public static ArrayListProductDao getInstance() {
        if (instance == null) {
            instance = new ArrayListProductDao();
        }
        return instance;
    }

    /**
     *
     * @param id
     * @return
     * @throws NullPointerException
     * @throws ProductNotFoundException
     */
    @Override
    public Product getProduct(Long id) throws ProductNotFoundException {
        if (id == null) {
            throw new NullPointerException("id should not be null");
        }
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new ProductNotFoundException(id, "the wrong id: "))
                .customerClone();
    }

    @Override
    public void changeProductStock(Long id, int newStock) {
        if (id == null) {
            throw new NullPointerException("id should not be null");
        }
        Product product = products.stream()
                                  .filter(p -> p.getId().equals(id))
                                  .findAny()
                                  .orElseThrow(() -> new ProductNotFoundException(id, "the wrong id: "));
        product.setStock(newStock);
    }

    @Override
    public List<Product> findProducts() {
        return products.stream()
                        .filter(p -> p.getPrice().signum() == 1 && p.getStock() > 0)
                        .collect(Collectors.toList());
    }

    @Override
    synchronized public void save(Product product) {
        if (product != null && !products.contains(product))
            products.add(product);
    }

    @Override
    synchronized public void delete(Long id) throws NullPointerException{
        if (id == null)
            throw new NullPointerException("id should not be null");
        products.removeIf(p -> p.getId().equals(id));
    }

    @Override
    public List<Product> productsSorting (Comparator<Product> comparator, List<Product> cloneProducts) {
        return cloneProducts.stream()
                            .sorted(comparator)
                            .collect(Collectors.toList());
    }

    @Override
    public void cleanListOfProducts() {
        products.clear();
    }
}
