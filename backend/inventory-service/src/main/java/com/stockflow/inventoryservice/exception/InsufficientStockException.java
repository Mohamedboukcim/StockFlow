package com.stockflow.inventoryservice.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(Long productId, Long warehouseId,
                                      int available, int requested) {
        super("Stock insuffisant pour le produit " + productId
                + " dans l'entrepôt " + warehouseId
                + " : disponible=" + available + ", demandé=" + requested);
    }
}