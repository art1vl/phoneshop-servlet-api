package com.es.phoneshop.exception;

public class ProductNotFoundException extends RuntimeException {//add mess
    private Long productId;
    private String message;

    public ProductNotFoundException(Long productId, String message) {
        this.productId = productId;
        this.message = message;
    }

    public Long getProductId() {
        return productId;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
