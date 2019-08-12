package com.es.phoneshop.model.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Cart implements Serializable {
    private List<CartItem> cartItems;
    private BigDecimal subCost;
    private Long totalQuantity;

    public Cart() {
        cartItems = new CopyOnWriteArrayList<>();
        subCost = new BigDecimal(0);
        totalQuantity = 0L;
    }

    public Cart(List<CartItem> cartItems, BigDecimal subCost, Long totalQuantity) {
        this.cartItems = Collections.unmodifiableList(cartItems);
        this.subCost = subCost;
        this.totalQuantity = totalQuantity;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public BigDecimal getSubCost() {
        return subCost;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public void setSubCost(BigDecimal subCost) {
        this.subCost = subCost;
    }

}
