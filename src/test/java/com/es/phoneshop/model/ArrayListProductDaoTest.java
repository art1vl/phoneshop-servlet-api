package com.es.phoneshop.model;

import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.PriceHistory;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
        ((ArrayListProductDao) productDao).cleanAllBase();
    }

    private ArrayList<PriceHistory> startPriceHistory(BigDecimal price) {
        ArrayList<PriceHistory> list = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");
        list.add(new PriceHistory("1.01.2019", new BigDecimal(200), usd));
        list.add(new PriceHistory("20.03.2019", new BigDecimal(250), usd));
        list.add(new PriceHistory("23.06.2019", price, usd));
        return list;
    }

    @Test
    public void listOfFoundProductsIsNotEmpty() {
        assertNotNull(productDao.findProducts());
        Product testProduct = new Product(20L, "sgs", "Samsung Galaxy S", new BigDecimal(100), null, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", startPriceHistory(new BigDecimal(2000)));

        productDao.save(testProduct);

        assertTrue(!productDao.findProducts().isEmpty());
    }

    @Test
    public void getProductWhichDoesNotExist() throws ProductNotFoundException{
        thrown.expect(ProductNotFoundException.class);
        thrown.expectMessage("-1");
        productDao.getProduct(-1L);
    }

    @Test
    public void getProductWithCorrectId(){
        Product testProduct = new Product(20L, "sgs", "Samsung Galaxy S", new BigDecimal(100), null, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", startPriceHistory(new BigDecimal(2000)));
        productDao.save(testProduct);

        Product receivedProduct = productDao.getProduct(20L);

        assertEquals(receivedProduct, testProduct);
        assertEquals(receivedProduct.getId(), testProduct.getId());
        assertEquals(receivedProduct.getCode(), testProduct.getCode());
        assertEquals(receivedProduct.getDescription(), testProduct.getDescription());
        assertTrue(receivedProduct.getPrice().compareTo(testProduct.getPrice()) == 0);
        assertEquals(receivedProduct.getCurrency(), testProduct.getCurrency());
        assertEquals(receivedProduct.getStock(), testProduct.getStock());
        assertEquals(receivedProduct.getImageUrl(), testProduct.getImageUrl());
    }

    @Test
    public void shouldNotFindProductWithZeroPriceInFilteredList() {
        Product testProduct = new Product(20L, "sgs", "Samsung Galaxy S", new BigDecimal(0), null, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", startPriceHistory(new BigDecimal(2000)));
        productDao.save(testProduct);

        List filteredList = productDao.findProducts();

        assertNotNull(filteredList);
        assertTrue(!filteredList.contains(testProduct));
    }

    @Test
    public void shouldNotFindProductWithZeroCostInFilteredList() {
        Product testProduct = new Product(20L, "sgs", "Samsung Galaxy S", new BigDecimal(100), null, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", startPriceHistory(new BigDecimal(2000)));
        productDao.save(testProduct);

        List filteredList = productDao.findProducts();

        assertNotNull(filteredList);
        assertTrue(!filteredList.contains(testProduct));
    }

    @Test
    public void shouldFindProductWhichSatisfyFind() {
        Product testProduct = new Product(20L, "sgs", "Samsung Galaxy S", new BigDecimal(100), null, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", startPriceHistory(new BigDecimal(2000)));
        productDao.save(testProduct);

        List filteredList = productDao.findProducts();

        assertNotNull(filteredList);
        assertTrue(filteredList.contains(testProduct));
    }

    @Test
    public void shouldNotFindProductWhichWasDeletedAndThrowProductNotFoundException(){
        productDao.save(new Product(10000L, "sgs", "Samsung Galaxy S", new BigDecimal(100), null, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", startPriceHistory(new BigDecimal(2000))));

        productDao.delete(10000L);

        thrown.expect(ProductNotFoundException.class);
        productDao.getProduct(10000L);
    }
}
