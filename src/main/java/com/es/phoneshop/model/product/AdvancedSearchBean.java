package com.es.phoneshop.model.product;

import java.math.BigDecimal;

public class AdvancedSearchBean {
    private String description;
    private Integer minStock;
    private Integer maxStock;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
//
//    public AdvancedSearchBean() {
//        this.description = null;
//        this.minPrice = null;
//        this.maxPrice = null;
//        this.minStock = null;
//        this.maxStock = null;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMinStock() {
        return minStock;
    }

    public void setMinStock(Integer minStock) {
        this.minStock = minStock;
    }

    public Integer getMaxStock() {
        return maxStock;
    }

    public void setMaxStock(Integer maxStock) {
        this.maxStock = maxStock;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }
}
