package com.tuiki.productservice.exception;

public class ProductNotFoundException extends RuntimeException {
    private final Integer productId;

    public ProductNotFoundException(Integer productId) {
        super("Producto con ID " + productId + " no encontrado");
        this.productId = productId;
    }

    public Integer getProductId() {
        return productId;
    }
}
