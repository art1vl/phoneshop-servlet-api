package com.es.phoneshop.service.product;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.dao.interfaces.ProductDao;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.PriceHistory;
import com.es.phoneshop.model.product.Product;
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

public class StockServiceImplTest {
    private StockService stockService;
    private ProductDao productDao;

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
        stockService = StockServiceImpl.getInstance();
        productDao = ArrayListProductDao.getInstance();
        productDao.cleanListOfProducts();
    }

    @Test
    public void shouldThrowOutOfStockException() {
        int stock = 10;
        Product testProduct = new Product(10000L, "sgs", "ZZZZ", new BigDecimal(100), null, stock, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", startPriceHistory(new BigDecimal(2000)));


        productDao.save(testProduct);
        List<CartItem> list = new ArrayList<>();
        list.add(new CartItem(testProduct, stock + 1));

        thrown.expect(OutOfStockException.class);
        stockService.changeProductsStock(list);
    }
}
