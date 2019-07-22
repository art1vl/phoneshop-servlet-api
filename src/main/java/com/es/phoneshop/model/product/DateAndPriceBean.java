package com.es.phoneshop.model.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

public class DateAndPriceBean implements Serializable {
    private String date;
    private BigDecimal price;
    private Currency currency;

    public DateAndPriceBean() {}

    public DateAndPriceBean(String date, BigDecimal price, Currency currency) {
        this.date = date;
        this.price = price;
        this.currency = currency;
    }

    public String getDate() {
        return date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
