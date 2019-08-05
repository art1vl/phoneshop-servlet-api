package com.es.phoneshop.exception;

public class ProductNotFoundException extends RuntimeException {
    private Long productId;

    public ProductNotFoundException(Long productId) {
        super(productId.toString());
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
