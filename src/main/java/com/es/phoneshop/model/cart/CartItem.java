package com.es.phoneshop.model.cart;

public class CartItem {
    private Long productId;
    private Long quantity;

    public CartItem(Long productId, Long quantity) {
        if (productId == null) {
            throw new IllegalArgumentException("productId is required");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity should be a positive number");
        }
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
