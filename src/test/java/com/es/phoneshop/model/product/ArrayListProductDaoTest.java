package com.es.phoneshop.model.product;

import com.es.phoneshop.Exception.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;
    private Product testProduct;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
        testProduct = new Product(20L, "sgs", "Samsung Galaxy S", new BigDecimal(100), null, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(testProduct);
    }

    @Test
    public void testFindProductsNoResults() {
        assertTrue(!productDao.findProducts().isEmpty());
    }

    @Test (expected = ProductNotFoundException.class)
    public void testGetProductWithIncorrectId(){
        productDao.getProduct(-1L);
    }

    @Test
    public void testGetProductWithCorrectId(){
        assertTrue(productDao.getProduct(20L).equals(testProduct));
    }

    @Test
    public void testFindProductsNotFitToFilter() {
        int sizeBeforeSave = productDao.findProducts().size();
        productDao.save(new Product(21L, "sgs", "Samsung Galaxy S", new BigDecimal(100), null, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        assertTrue(productDao.findProducts().size() == sizeBeforeSave);
    }

    @Test
    public void testFindProductsFitToFilter() {
        int sizeBeforeSave = productDao.findProducts().size();
        productDao.save(new Product(22L, "sgs", "Samsung Galaxy S", new BigDecimal(100), null, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        assertTrue(productDao.findProducts().size() == sizeBeforeSave + 1);
    }

    @Test (expected = ProductNotFoundException.class)
    public void testDeleteProductWithIncorrectId(){
        productDao.save(new Product(10000L, "sgs", "Samsung Galaxy S", new BigDecimal(100), null, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.delete(10000L);
        productDao.getProduct(10000L);
    }
}
