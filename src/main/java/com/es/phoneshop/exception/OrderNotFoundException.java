package com.es.phoneshop.exception;

public class OrderNotFoundException extends RuntimeException {
    private String secureId;
    private String message;

    public OrderNotFoundException(String secureId, String message) {
        this.secureId = secureId;
        this.message = message;
    }

    public String getSecureId() {
        return secureId;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
