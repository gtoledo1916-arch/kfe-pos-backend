package org.kfe.api.demo.exception;

public class ProductoNoEncontradoException extends RuntimeException {
    public ProductoNoEncontradoException(String message) {
        super("Producto no encontrado — " + message);
    }
}
