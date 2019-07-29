package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.DateAndPriceBean;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SampleDataListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if("true".equals(servletContextEvent.getServletContext().getInitParameter("insertSampleData"))) {
            ProductDao productDao = ArrayListProductDao.getInstance();
            fillProductList().forEach(productDao::save);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private List<Product> fillProductList() {
        List<Product> startProducts = new CopyOnWriteArrayList<>();
        Currency usd = Currency.getInstance("USD");
        startProducts.add(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg",
                startPriceHistory(new BigDecimal(100))));
        startProducts.add(new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg",
                startPriceHistory(new BigDecimal(200))));
        startProducts.add(new Product(3L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg",
                startPriceHistory(new BigDecimal(300))));
        startProducts.add(new Product(4L, "iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg",
                startPriceHistory(new BigDecimal(200))));
        startProducts.add(new Product(5L, "iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg",
                startPriceHistory(new BigDecimal(1000))));
        startProducts.add(new Product(6L, "htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg",
                startPriceHistory(new BigDecimal(320))));
        startProducts.add(new Product(7L, "sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg",
                startPriceHistory(new BigDecimal(420))));
        startProducts.add(new Product(8L, "xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg",
                startPriceHistory(new BigDecimal(120))));
        startProducts.add(new Product(9L, "nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg",
                startPriceHistory(new BigDecimal(70))));
        startProducts.add(new Product(10L, "palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg",
                startPriceHistory(new BigDecimal(170))));
        startProducts.add(new Product(11L, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg",
                startPriceHistory(new BigDecimal(70))));
        startProducts.add(new Product(12L, "simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg",
                startPriceHistory(new BigDecimal(80))));
        startProducts.add(new Product(13L, "simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg",
                startPriceHistory(new BigDecimal(150))));
        return startProducts;
    }

    private ArrayList<DateAndPriceBean> startPriceHistory(BigDecimal price) {
        ArrayList<DateAndPriceBean> list = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");
        list.add(new DateAndPriceBean("10.01.2019", new BigDecimal(200), usd));
        list.add(new DateAndPriceBean("20.03.2019", new BigDecimal(250), usd));
        list.add(new DateAndPriceBean("15.05.2019", new BigDecimal(300), usd));
        list.add(new DateAndPriceBean("23.06.2019", price, usd));
        return list;
    }
}
