package com.es.phoneshop.model.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;

public class PriceHistory implements Serializable {
    private Date date;
    private BigDecimal price;
    private Currency currency;
    private String stringDate;

    public PriceHistory() {}

    public PriceHistory(Date date, BigDecimal price, Currency currency) {
        this.date = date;
        this.price = price;
        this.currency = currency;
        SimpleDateFormat ddMmmYyyyFormat = new SimpleDateFormat("dd.MM.yyyy");
        this.stringDate = ddMmmYyyyFormat.format(date);
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

    public String getStringDate() {
        return stringDate;
    }

    public void setStringDate(String stringDate) {
        this.stringDate = stringDate;
    }
}
