//package com.es.phoneshop.model;
//
//import com.es.phoneshop.model.product.HttpSessionRecentlyViewedService;
//import com.es.phoneshop.model.product.PriceHistory;
//import com.es.phoneshop.model.product.Product;
//import org.junit.Before;
//import org.junit.Test;
//
//import javax.servlet.http.HttpServletRequest;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Currency;
//import java.util.Deque;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.mock;
//
//public class HttpSessionRecentlyViewedServiceTest {
//    private HttpSessionRecentlyViewedService recentlyViewedService;
//
//    @Before
//    public void setup() {
//        recentlyViewedService = HttpSessionRecentlyViewedService.getInstance();
//    }
//
//    private ArrayList<PriceHistory> startPriceHistory(BigDecimal price) {
//        ArrayList<PriceHistory> list = new ArrayList<>();
//        Currency usd = Currency.getInstance("USD");
//        list.add(new PriceHistory("1.01.2019", new BigDecimal(200), usd));
//        list.add(new PriceHistory("20.03.2019", new BigDecimal(250), usd));
//        list.add(new PriceHistory("23.06.2019", price, usd));
//        return list;
//    }
//
//    @Test
//    public void shouldArrayAtZeroPositionContainsProductWhichWasAdded() {
//        Product testProduct = new Product(10000L, "sgs", "aaaa", new BigDecimal(100000), null, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", startPriceHistory(new BigDecimal(2000)));
//        HttpServletRequest request = mock(HttpServletRequest.class);
//
//        Deque<Product> result = recentlyViewedService.getQueue(request);
//        result = recentlyViewedService.refreshList(result, testProduct);
//
//        assertNotNull(result);
//        assertEquals(result.peekFirst(), testProduct);
//        assertEquals(result.peekFirst().getId(), testProduct.getId());
//        assertEquals(result.peekFirst().getCode(), testProduct.getCode());
//        assertEquals(result.peekFirst().getDescription(), testProduct.getDescription());
//        assertTrue(result.peekFirst().getPrice().compareTo(testProduct.getPrice()) == 0);
//        assertEquals(result.peekFirst().getCurrency(), testProduct.getCurrency());
//        assertEquals(result.peekFirst().getStock(), testProduct.getStock());
//        assertEquals(result.peekFirst().getImageUrl(), testProduct.getImageUrl());
//    }
//}
