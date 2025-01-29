package com.suganame.payment_gateway.exceptions;

public class ProductOutOfStockException extends RuntimeException{
    public ProductOutOfStockException(String message) {
        super(message);
    }
}
