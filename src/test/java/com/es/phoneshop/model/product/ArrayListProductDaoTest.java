package com.es.phoneshop.model.product;

import com.es.phoneshop.exception.ProductNotFoundException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;
    private Product testProduct;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
    }

    private ArrayList<DateAndPriceBean> startPriceHistory(BigDecimal price) {
        ArrayList<DateAndPriceBean> list = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");
        list.add(new DateAndPriceBean("1.01.2019", new BigDecimal(200), usd));
        list.add(new DateAndPriceBean("20.03.2019", new BigDecimal(250), usd));
        list.add(new DateAndPriceBean("23.06.2019", price, usd));
        return list;
    }

    @Test
    public void listOfFoundProductsIsNotEmpty() {
        assertNotNull(productDao.findProducts(null, null, null));
        assertTrue(!productDao.findProducts(null, null, null).isEmpty());
    }

    @Test
    public void getProductWhichDoesNotExist() throws ProductNotFoundException{
        thrown.expect(ProductNotFoundException.class);
        thrown.expectMessage("There is no product with -1 id");
        productDao.getProduct(-1L);
    }

    @Test
    public void getProductWithCorrectId(){
        testProduct = new Product(20L, "sgs", "Samsung Galaxy S", new BigDecimal(100), null, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", startPriceHistory(new BigDecimal(2000)));
        productDao.save(testProduct);

        Product receivedProduct = productDao.getProduct(20L);

        assertEquals(receivedProduct, testProduct);
    }

    @Test
    public void shouldNotFindProductWithZeroPriceInFilteredList() {//check products for 1 valid thing
        testProduct = new Product(20L, "sgs", "Samsung Galaxy S", new BigDecimal(0), null, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", startPriceHistory(new BigDecimal(2000)));
        productDao.save(testProduct);

        ArrayList<Product> filteredList = (ArrayList) productDao.findProducts(null, null, null);

        assertNotNull(filteredList);
        assertTrue(!filteredList.contains(testProduct));
    }

    @Test
    public void shouldNotFindProductWithZeroCostInFilteredList() {//check products for 1 valid thing
        testProduct = new Product(20L, "sgs", "Samsung Galaxy S", new BigDecimal(100), null, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", startPriceHistory(new BigDecimal(2000)));
        productDao.save(testProduct);

        ArrayList<Product> filteredList = (ArrayList) productDao.findProducts(null, null, null);

        assertNotNull(filteredList);
        assertTrue(!filteredList.contains(testProduct));
    }

    @Test
    public void shouldFindProductWhichSatisfyFilter() {
        testProduct = new Product(20L, "sgs", "Samsung Galaxy S", new BigDecimal(100), null, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", startPriceHistory(new BigDecimal(2000)));
        productDao.save(testProduct);

        ArrayList<Product> filteredList = (ArrayList) productDao.findProducts(null, null, null);

        assertNotNull(filteredList);
        assertTrue(filteredList.contains(testProduct));
    }

    @Test
    public void shouldNotFindProductWhichWasDeletedAndThrowProductNotFoundException(){
        productDao.save(new Product(10000L, "sgs", "Samsung Galaxy S", new BigDecimal(100), null, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", startPriceHistory(new BigDecimal(2000))));

        productDao.delete(10000L);

        thrown.expect(ProductNotFoundException.class);
        thrown.expectMessage("There is no product with 10000 id");
        productDao.getProduct(10000L);
    }

    @Test
    public void shouldSortedByDescDescriptionArrayAtZeroPositionContainsProductWhichWasAdded() {
        productDao.save(new Product(10000L, "sgs", "ZZZZ", new BigDecimal(100), null, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", startPriceHistory(new BigDecimal(2000))));

        ArrayList<Product> filteredList = (ArrayList) productDao.findProducts("description", "desc", null);

        assertNotNull(filteredList);
        assertTrue(filteredList.get(0).getDescription().equals("ZZZZ"));
    }

    @Test
    public void shouldArrayAtZeroPositionContainsProductWhichSatisfyQuery() {
        productDao.save(new Product(10000L, "sgs", "ZZZZ", new BigDecimal(100), null, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", startPriceHistory(new BigDecimal(2000))));

        ArrayList<Product> filteredList = (ArrayList) productDao.findProducts(null,null,"ZZZ");

        assertNotNull(filteredList);
        assertTrue(filteredList.get(0).getDescription().equals("ZZZZ"));
    }
}
