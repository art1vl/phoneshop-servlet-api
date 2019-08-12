package com.es.phoneshop.service.product;

import com.es.phoneshop.service.product.HttpSessionRecentlyViewedService;
import com.es.phoneshop.service.product.RecentlyViewed;
import com.es.phoneshop.model.product.PriceHistory;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Deque;

import static org.junit.Assert.*;

public class HttpSessionRecentlyViewedServiceTest {
    private RecentlyViewed recentlyViewedService;

    @Before
    public void setup() {
        recentlyViewedService = HttpSessionRecentlyViewedService.getInstance();
    }

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

    @Test
    public void shouldArrayAtZeroPositionContainsProductWhichWasAdded() {
        Product testProduct = new Product(10000L, "sgs", "aaaa", new BigDecimal(100000), null, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", startPriceHistory(new BigDecimal(2000)));
        HttpServletRequest requestMock = Mockito.mock(HttpServletRequest.class);
        HttpSession sessionMock = Mockito.mock(HttpSession.class);

        Mockito.when(requestMock.getSession()).thenReturn(sessionMock);
        Mockito.when(sessionMock.getAttribute("recentlyViewed")).thenReturn(new ArrayDeque<Product>());

        recentlyViewedService.refreshList(requestMock, testProduct);
        Deque<Product> result = recentlyViewedService.getQueue(requestMock);

        assertNotNull(result);
        assertEquals(result.peekFirst(), testProduct);
        assertEquals(result.peekFirst().getId(), testProduct.getId());
        assertEquals(result.peekFirst().getCode(), testProduct.getCode());
        assertEquals(result.peekFirst().getDescription(), testProduct.getDescription());
        assertTrue(result.peekFirst().getPrice().compareTo(testProduct.getPrice()) == 0);
        assertEquals(result.peekFirst().getCurrency(), testProduct.getCurrency());
        assertEquals(result.peekFirst().getStock(), testProduct.getStock());
        assertEquals(result.peekFirst().getImageUrl(), testProduct.getImageUrl());
    }
}
