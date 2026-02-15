package org.kfe.api.demo.exception;

public class UsuarioNoEncontradoException extends RuntimeException{

    public UsuarioNoEncontradoException(String email) {
        super("Usuario no encontrado: " + email);
    }
}
