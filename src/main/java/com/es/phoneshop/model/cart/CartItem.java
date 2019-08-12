package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import java.io.Serializable;
import java.math.BigDecimal;

public final class CartItem implements Serializable {
    private Product product;
    private Long quantity;
    private BigDecimal totalCost;

    public CartItem() {}

    public CartItem(Product product, Long quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity should be a positive number");
        }
        this.product = product;
        this.quantity = quantity;
        this.totalCost = product.getPrice().multiply(new BigDecimal(quantity));
    }

    public Product getProduct() {
        return product;
    }

    public Long getQuantity() {
        return quantity;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    @Override
    public boolean equals(Object o) {
        return this.product.equals(o);
    }
}
