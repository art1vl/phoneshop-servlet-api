package com.es.phoneshop.exception;

public class ProductNotFoundException extends RuntimeException {
    private long productId;

    public ProductNotFoundException(Long productId) {
        this.productId = productId;
    }
}
