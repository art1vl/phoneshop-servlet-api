package com.es.phoneshop.model.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Cart implements Serializable {
    private List<CartItem> cartItems;
    private BigDecimal totalCost;
    private Long totalQuantity;

    public Cart() {
        cartItems = new CopyOnWriteArrayList<>();
        totalCost = new BigDecimal(0);
        totalQuantity = 0L;
    }

    public Cart(List<CartItem> cartItems, BigDecimal totalCost, Long totalQuantity) {
        this.cartItems = cartItems;
        this.totalCost = totalCost;
        this.totalQuantity = totalQuantity;
    }

    @Override
    protected Cart clone() {
        return new Cart(this.cartItems, this.totalCost, this.totalQuantity);
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

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

}
