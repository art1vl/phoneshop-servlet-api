package com.es.phoneshop.exception;

public class OutOfStockException extends RuntimeException{
    private int maxStock;
    private String message;

    public OutOfStockException(int maxStock, String message) {
        this.maxStock = maxStock;
        this.message = message;
    }

    public int getMaxStock() {
        return maxStock;
    }

    @Override
    public String getMessage() {
        return message + maxStock;
    }
}
