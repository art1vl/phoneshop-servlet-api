package com.es.phoneshop.service.product;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.dao.interfaces.ProductDao;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.product.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;

public class DefaultProductServiceTest {
    private ProductDao productDao;
    private DefaultProductService daoService;

    private ArrayList<PriceHistory> startPriceHistory(BigDecimal price) {
        ArrayList<PriceHistory> list = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        try {
            list.add(new PriceHistory(format.parse("1.01.2019"), new BigDecimal(200), usd));
            list.add(new PriceHistory(format.parse("20.03.2019"), new BigDecimal(250), usd));
            list.add(new PriceHistory(format.parse("23.06.2019"), price, usd));
        }
        catch (ParseException pe) {

        }
        return list;
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        daoService = DefaultProductService.getInstance();
        productDao = ArrayListProductDao.getInstance();
        productDao.cleanListOfProducts();
    }

    @Test
    public void shouldSortedByDescDescriptionArrayAtZeroPositionContainsProductWhichWasAdded() {
        Product testProduct = new Product(10000L, "sgs", "ZZZZ", new BigDecimal(100), null, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", startPriceHistory(new BigDecimal(2000)));
        daoService.save(testProduct);

        List<Product> filteredList = daoService.findAndSortProducts("description", "desc", null);

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
        daoService.save(testProduct);

        List<Product> filteredList = daoService.findAndSortProducts("description", "asc", null);

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
        daoService.save(testProduct);

        List<Product> filteredList = daoService.findAndSortProducts("price", "desc", null);

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
        daoService.save(testProduct);

        List<Product> filteredList = daoService.findAndSortProducts("price", "asc", null);

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
        daoService.save(testProduct);

        List<Product> filteredList =  daoService.findAndSortProducts(null,null,"ZZZ");

        assertNotNull(filteredList);
        assertEquals(filteredList.get(0), testProduct);
        assertEquals(filteredList.get(0).getId(), testProduct.getId());
        assertEquals(filteredList.get(0).getCode(), testProduct.getCode());
        assertEquals(filteredList.get(0).getDescription(), testProduct.getDescription());
        assertEquals(0, filteredList.get(0).getPrice().compareTo(testProduct.getPrice()));
        assertEquals(filteredList.get(0).getCurrency(), testProduct.getCurrency());
        assertEquals(filteredList.get(0).getStock(), testProduct.getStock());
        assertEquals(filteredList.get(0).getImageUrl(), testProduct.getImageUrl());
    }

    @Test
    public void shouldGetTheProductWhichWasSaved() {
        Product testProduct = new Product(10000L, "sgs", "ZZZZ", new BigDecimal(100), null, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", startPriceHistory(new BigDecimal(2000)));
        daoService.save(testProduct);

        Product productReceivedByGet = daoService.getProduct(10000L);

        assertEquals(testProduct.getImageUrl(), productReceivedByGet.getImageUrl());
        assertEquals(testProduct.getStock(), productReceivedByGet.getStock());
        assertEquals(testProduct.getCurrency(), productReceivedByGet.getCurrency());
        assertEquals(0, testProduct.getPrice().compareTo(productReceivedByGet.getPrice()));
        assertEquals(testProduct.getDescription(), productReceivedByGet.getDescription());
        assertEquals(testProduct.getCode(), productReceivedByGet.getCode());
        assertEquals(testProduct.getId(), productReceivedByGet.getId());
    }

    @Test
    public void shouldNotFindProductWhichWasDeletedAndThrowProductNotFoundException() {
        daoService.save(new Product(10000L, "sgs", "Samsung Galaxy S", new BigDecimal(100), null, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", startPriceHistory(new BigDecimal(2000))));

        daoService.delete(10000L);

        thrown.expect(ProductNotFoundException.class);
        productDao.getProduct(10000L);
    }

    @Test
    public void shouldChangeCorrectProductStock() {
        daoService.save(new Product(10000L, "sgs", "Samsung Galaxy S", new BigDecimal(100), null, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", startPriceHistory(new BigDecimal(2000))));
        int newStock = 3;

        daoService.changeProductStock(10000L, newStock);
        int realProductStock = daoService.getProduct(10000L).getStock();

        assertEquals(realProductStock, newStock);
    }
}
