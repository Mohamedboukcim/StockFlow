package com.stockflow.productservice.exception;

public class DuplicateSkuException extends RuntimeException {
    public DuplicateSkuException(String sku) {
        super("Un produit avec le SKU '" + sku + "' existe déjà");
    }
}