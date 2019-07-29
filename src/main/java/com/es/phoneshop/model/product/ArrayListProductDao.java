package com.es.phoneshop.model.product;

import com.es.phoneshop.exception.ProductNotFoundException;

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

    @Override
    public Product getProduct(Long id) throws NullPointerException, ProductNotFoundException {
        if (id == null)
            throw new NullPointerException("null id in getProduct()");
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public List<Product> findProducts(String sort, String order, String query) {
        List<Product> cloneProducts = products.stream()
                                        .filter(p -> p.getPrice().signum() == 1 && p.getStock() > 0)
                                        .collect(Collectors.toList());
        if (query != null && !query.equals(""))
            cloneProducts = findProductsAccordingQuery(query, cloneProducts);
        if (sort != null)
            cloneProducts = productsSorting(sort, order, cloneProducts);
        return cloneProducts;
    }

    @Override
    synchronized public void save(Product product) {
        if (product != null && !products.contains(product))
            products.add(product);
    }

    @Override
    public void delete(Long id) throws NullPointerException{
        if (id == null)
            throw new NullPointerException("null id in delete()");
        products.removeIf(p -> p.getId().equals(id));
    }

    private List<Product> findProductsAccordingQuery (String query, List<Product> cloneProducts) {
        Set<Product> finalSet = new LinkedHashSet<>(cloneProducts.stream()
                                                    .filter((o) -> o.getDescription().toLowerCase().contains(query.toLowerCase()))
                                                    .collect(Collectors.toSet()));
        String[] arrayOfWordsFromQuery = query.split(" ");
        Set<Product> temporarySet = new HashSet<>();
        for (String word: arrayOfWordsFromQuery){
            temporarySet.clear();
            temporarySet = cloneProducts.stream()
                                    .filter((o) -> o.getDescription().toLowerCase().contains(word.toLowerCase()))
                                    .collect(Collectors.toSet());
            for (Product product: temporarySet)
                finalSet.add(product);
        }
        List<Product> finalList = new ArrayList<>(finalSet);
        return finalList;

    }

    private List<Product> productsSorting (String sort, String order, List<Product> cloneProducts) {
        switch (sort){
            case "description":
                switch (order){
                    case "asc":
                        cloneProducts = cloneProducts.stream()
                                   .sorted(Comparator.comparing(Product::getDescription))
                                   .collect(Collectors.toList());
                        return cloneProducts;
                    case "desc":
                        cloneProducts = cloneProducts.stream()
                                   .sorted(Comparator.comparing(Product::getDescription)
                                   .reversed())
                                   .collect(Collectors.toList());
                        return cloneProducts;
                }
            case "price":
                switch (order){
                    case "asc":
                        cloneProducts = cloneProducts.stream()
                                   .sorted((o1, o2) -> o1.getPrice().subtract(o2.getPrice()).intValue())
                                   .collect(Collectors.toList());
                        return cloneProducts;
                    case "desc":
                        cloneProducts = cloneProducts.stream()
                                   .sorted((o1, o2) -> o2.getPrice().subtract(o1.getPrice()).intValue())
                                   .collect(Collectors.toList());
                        return cloneProducts;
                }
        }
        return cloneProducts;
    }
}
