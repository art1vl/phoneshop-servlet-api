package com.es.phoneshop.model.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

public class PriceHistory implements Serializable {
    private Date date;
    private BigDecimal price;
    private Currency currency;

    public PriceHistory() {}

    public PriceHistory(Date date, BigDecimal price, Currency currency) {
        this.date = date;
        this.price = price;
        this.currency = currency;
    }

    @Override
    protected PriceHistory clone() {
        return new PriceHistory(this.date, this.price, this.currency);
    }

    public Date getDate() {
        return date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
