package com.es.phoneshop.exception;

public class OutOfStockException extends Exception{
    private int maxStock;

    public OutOfStockException(int maxStock) {
        this.maxStock = maxStock;
    }

    public int getMaxStock() {
        return maxStock;
    }
}
