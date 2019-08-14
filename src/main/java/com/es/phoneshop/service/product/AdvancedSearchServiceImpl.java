package com.es.phoneshop.service.product;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.dao.interfaces.ProductDao;
import com.es.phoneshop.model.product.AdvancedSearchBean;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdvancedSearchServiceImpl implements AdvancedSearchService {
    private static AdvancedSearchServiceImpl instance;

    private ProductService productService;

    private AdvancedSearchServiceImpl() {
        productService = DefaultProductService.getInstance();
    }

    synchronized public static AdvancedSearchServiceImpl getInstance() {
        if(instance == null){
            instance = new AdvancedSearchServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean checkDate(HttpServletRequest request, AdvancedSearchBean query) {
        boolean exceptionFlag = false;

        String description = request.getParameter("description");
        if ((description == null)
                || (!description.matches("^[a-zA-Z]*$"))) {
            exceptionFlag = true;
            request.setAttribute("errorDescription", "Incorrect description");
        }
        else {
            query.setDescription(description);
        }
        if (checkInt(request, "maxPrice", "errorMaxPrice", query)) {
            exceptionFlag = true;
        }
        if (checkInt(request, "maxStock", "errorMaxStock", query)) {
            exceptionFlag = true;
        }
        if (checkInt(request, "minPrice", "errorMinPrice", query)) {
            exceptionFlag = true;
        }
        if (checkInt(request, "minStock", "errorMinStock", query)) {
            exceptionFlag = true;
        }
        return exceptionFlag;
    }

    @Override
    public List<Product> finalResult(HttpServletRequest request, AdvancedSearchBean query) {
        List<Product> list = new ArrayList<>();
        if(query.getDescription() != null) {
            list = productService.findAndSortProducts(null, null, query.getDescription());
        }
        if(query.getMaxPrice() != null || query.getMinPrice() != null) {
            list = sortByPrice(query, list);
        }
        if(query.getMaxStock() != null || query.getMinStock() != null) {
            list = sortByStock(query, list);
        }
        return list;
    }

    private List<Product> sortByPrice(AdvancedSearchBean query, List<Product> list) {
        BigDecimal minPrice = query.getMinPrice();
        BigDecimal maxPrice = query.getMaxPrice();
        if ((minPrice != null) && (maxPrice != null)) {
            list = list.stream()
                    .filter(p -> p.getPrice().compareTo(minPrice) >= 0 && p.getPrice().compareTo(maxPrice) <= 0)
                    .collect(Collectors.toList());
        }
        return list;
    }

    private List<Product> sortByStock(AdvancedSearchBean query, List<Product> list) {
        Integer minStock = query.getMinStock();
        Integer maxStock = query.getMaxStock();
        if ((minStock != null) && (maxStock != null)) {
            list = list.stream()
                    .filter(p -> p.getStock() >= minStock && p.getStock() <= maxStock)
                    .collect(Collectors.toList());
        }
        return list;
    }

    private boolean checkInt(HttpServletRequest request, String attributeName, String errorName, AdvancedSearchBean query) {
        boolean flag = false;
        String attributeItem = request.getParameter(attributeName);
        switch (attributeName) {
            case "maxPrice":
                try {
                    if (attributeItem == "") {
                        query.setMaxPrice(null);
                    }
                    else {
                        query.setMaxPrice(new BigDecimal(Integer.valueOf(attributeItem)));
                    }
                    break;
                }
                catch (NumberFormatException ex) {
                    request.setAttribute(errorName, "Incorrect number");
                    flag = true;
                    break;
                }
            case "maxStock":
                try {
                    if (attributeItem == "") {
                        query.setMaxStock(null);
                    }
                    else {
                        query.setMaxStock(Integer.valueOf(attributeItem));
                    }
                    break;
                }
                catch (NumberFormatException ex) {
                    request.setAttribute(errorName, "Incorrect number");
                    flag = true;
                    break;
                }
            case "minPrice":
                try {
                    if (attributeItem == "") {
                        query.setMinPrice(null);
                    }
                    else {
                        query.setMinPrice(new BigDecimal(Integer.valueOf(attributeItem)));
                    }
                    break;
                }
                catch (NumberFormatException ex) {
                    request.setAttribute(errorName, "Incorrect number");
                    flag = true;
                    break;
                }
            case "minStock":
                try {
                    if (attributeItem == "") {
                        query.setMinStock(null);
                    }
                    else {
                        query.setMinStock(Integer.valueOf(attributeItem));
                    }
                    break;
                }
                catch (NumberFormatException ex) {
                    request.setAttribute(errorName, "Incorrect number");
                    flag = true;
                    break;
                }
        }
        return flag;
    }

}
