package com.es.phoneshop.model.product;

import java.util.*;
import java.util.stream.Collectors;

public class HttpSessionDaoService implements DaoService {
    private static HttpSessionDaoService instance;

    private ArrayListProductDao arrayListProductDao;

    private HttpSessionDaoService() {}

    synchronized public static HttpSessionDaoService getInstance() {
        if(instance == null){
            instance = new HttpSessionDaoService();
        }
        return instance;
    }

    @Override
    public List<Product> handler(String sort, String order, String query) {
        arrayListProductDao = ArrayListProductDao.getInstance();
        List<Product> cloneProducts = arrayListProductDao.findProducts();
        if (query != null && !query.equals(""))
            cloneProducts = findProductsAccordingQuery(query,cloneProducts);
        if (sort != null)
            cloneProducts = productsSorting(sort, order, cloneProducts);
        return cloneProducts;
    }

    @Override
    public List<Product> productsSorting(String sort, String order, List<Product> cloneProducts) {
        List<Product> sortedProducts;
        Comparator<Product> comparator = Comparator.comparing(Product::getDescription);
        switch (sort){
            case "description":
                switch (order){
                    case "desc":
                        comparator = Comparator.comparing(Product::getDescription).reversed();
                        break;
                }
                break;
            case "price":
                switch (order){
                    case "asc":
                        comparator = (o1, o2) -> o1.getPrice().subtract(o2.getPrice()).intValue();
                        break;
                    case "desc":
                        comparator = (o1, o2) -> o2.getPrice().subtract(o1.getPrice()).intValue();
                        break;
                }
        }
        sortedProducts = arrayListProductDao.productsSorting(comparator, cloneProducts);
        return sortedProducts;
    }

    @Override
    public List<Product> findProductsAccordingQuery(String query, List<Product> cloneProducts) {
        final String correctQuery = query.trim().replaceAll("\\s+", " ");
        Set<Product> finalSet = new LinkedHashSet<>(cloneProducts.stream()
                .filter(o -> o.getDescription().toLowerCase().contains(correctQuery.toLowerCase()))
                .collect(Collectors.toSet()));
        String[] arrayOfWordsFromQuery = correctQuery.split(" ");
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
}
