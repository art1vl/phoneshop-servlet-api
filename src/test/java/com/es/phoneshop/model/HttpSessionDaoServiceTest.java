package com.es.phoneshop.model;

import com.es.phoneshop.model.product.*;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;

public class HttpSessionDaoServiceTest {
    private ProductDao productDao;
    private HttpSessionDaoService daoService;

    private ArrayList<PriceHistory> startPriceHistory(BigDecimal price) {
        ArrayList<PriceHistory> list = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");
        list.add(new PriceHistory("1.01.2019", new BigDecimal(200), usd));
        list.add(new PriceHistory("20.03.2019", new BigDecimal(250), usd));
        list.add(new PriceHistory("23.06.2019", price, usd));
        return list;
    }

    @Before
    public void setup() {
        daoService = HttpSessionDaoService.getInstance();
        productDao = ArrayListProductDao.getInstance();
        ((ArrayListProductDao) productDao).cleanAllBase();
    }

    @Test
    public void shouldSortedByDescDescriptionArrayAtZeroPositionContainsProductWhichWasAdded() {
        Product testProduct = new Product(10000L, "sgs", "ZZZZ", new BigDecimal(100), null, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", startPriceHistory(new BigDecimal(2000)));
        productDao.save(testProduct);

        List<Product> filteredList = daoService.handler("description", "desc", null);

        assertNotNull(filteredList);
        assertEquals(filteredList.get(0), testProduct);
        assertEquals(filteredList.get(0).getId(), testProduct.getId());
        assertEquals(filteredList.get(0).getCode(), testProduct.getCode());
        assertEquals(filteredList.get(0).getDescription(), testProduct.getDescription());
        assertTrue(filteredList.get(0).getPrice().compareTo(testProduct.getPrice()) == 0);
        assertEquals(filteredList.get(0).getCurrency(), testProduct.getCurrency());
        assertEquals(filteredList.get(0).getStock(), testProduct.getStock());
        assertEquals(filteredList.get(0).getImageUrl(), testProduct.getImageUrl());
    }

    @Test
    public void shouldSortedByAscDescriptionArrayAtZeroPositionContainsProductWhichWasAdded() {
        Product testProduct = new Product(10000L, "sgs", "aaaaaaa", new BigDecimal(100), null, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", startPriceHistory(new BigDecimal(2000)));
        productDao.save(testProduct);

        List<Product> filteredList = daoService.handler("description", "asc", null);

        assertNotNull(filteredList);
        assertEquals(filteredList.get(0), testProduct);
        assertEquals(filteredList.get(0).getId(), testProduct.getId());
        assertEquals(filteredList.get(0).getCode(), testProduct.getCode());
        assertEquals(filteredList.get(0).getDescription(), testProduct.getDescription());
        assertTrue(filteredList.get(0).getPrice().compareTo(testProduct.getPrice()) == 0);
        assertEquals(filteredList.get(0).getCurrency(), testProduct.getCurrency());
        assertEquals(filteredList.get(0).getStock(), testProduct.getStock());
        assertEquals(filteredList.get(0).getImageUrl(), testProduct.getImageUrl());
    }

    @Test
    public void shouldSortedByDescPriceArrayAtZeroPositionContainsProductWhichWasAdded() {
        Product testProduct = new Product(10000L, "sgs", "aaaa", new BigDecimal(100000), null, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", startPriceHistory(new BigDecimal(2000)));
        productDao.save(testProduct);

        List<Product> filteredList = daoService.handler("price", "desc", null);

        assertNotNull(filteredList);
        assertEquals(filteredList.get(0), testProduct);
        assertEquals(filteredList.get(0).getId(), testProduct.getId());
        assertEquals(filteredList.get(0).getCode(), testProduct.getCode());
        assertEquals(filteredList.get(0).getDescription(), testProduct.getDescription());
        assertTrue(filteredList.get(0).getPrice().compareTo(testProduct.getPrice()) == 0);
        assertEquals(filteredList.get(0).getCurrency(), testProduct.getCurrency());
        assertEquals(filteredList.get(0).getStock(), testProduct.getStock());
        assertEquals(filteredList.get(0).getImageUrl(), testProduct.getImageUrl());
    }

    @Test
    public void shouldSortedByAscPriceArrayAtZeroPositionContainsProductWhichWasAdded() {
        Product testProduct = new Product(10000L, "sgs", "aaaa", new BigDecimal(1), null, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", startPriceHistory(new BigDecimal(2000)));
        productDao.save(testProduct);

        List<Product> filteredList = daoService.handler("price", "asc", null);

        assertNotNull(filteredList);
        assertEquals(filteredList.get(0), testProduct);
        assertEquals(filteredList.get(0).getId(), testProduct.getId());
        assertEquals(filteredList.get(0).getCode(), testProduct.getCode());
        assertEquals(filteredList.get(0).getDescription(), testProduct.getDescription());
        assertTrue(filteredList.get(0).getPrice().compareTo(testProduct.getPrice()) == 0);
        assertEquals(filteredList.get(0).getCurrency(), testProduct.getCurrency());
        assertEquals(filteredList.get(0).getStock(), testProduct.getStock());
        assertEquals(filteredList.get(0).getImageUrl(), testProduct.getImageUrl());
    }

    @Test
    public void shouldArrayAtZeroPositionContainsProductWhichSatisfyQuery() {
        Product testProduct = new Product(10000L, "sgs", "ZZZZ", new BigDecimal(100), null, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", startPriceHistory(new BigDecimal(2000)));
        productDao.save(testProduct);

        List<Product> filteredList =  daoService.handler(null,null,"ZZZ");

        assertNotNull(filteredList);
        assertEquals(filteredList.get(0), testProduct);
        assertEquals(filteredList.get(0).getId(), testProduct.getId());
        assertEquals(filteredList.get(0).getCode(), testProduct.getCode());
        assertEquals(filteredList.get(0).getDescription(), testProduct.getDescription());
        assertTrue(filteredList.get(0).getPrice().compareTo(testProduct.getPrice()) == 0);
        assertEquals(filteredList.get(0).getCurrency(), testProduct.getCurrency());
        assertEquals(filteredList.get(0).getStock(), testProduct.getStock());
        assertEquals(filteredList.get(0).getImageUrl(), testProduct.getImageUrl());
    }
}
