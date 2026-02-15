package org.kfe.api.demo.exception;

public class RolInvalidoException extends RuntimeException {
    public RolInvalidoException(String rol) {
        super("El rol '" + rol + "' no es válido. Roles permitidos: ADMIN, GERENTE, CAJERO");
    }
}














