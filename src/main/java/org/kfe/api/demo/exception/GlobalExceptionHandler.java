package org.kfe.api.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CredencialesInvalidasException.class)
    public ResponseEntity<Apierror> handleCredenciales(CredencialesInvalidasException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new Apierror(401, ex.getMessage()));
    }

    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<Apierror> handleUsuarioNoEncontrado(UsuarioNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Apierror(404, ex.getMessage()));
    }



    @ExceptionHandler(RolInvalidoException.class)
    public ResponseEntity<Apierror> handleRolInvalido(RolInvalidoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Apierror(400, ex.getMessage()));
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Apierror> handleAccesoDenegado(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new Apierror(403, "No tienes permisos para realizar esta acción"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Apierror> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Apierror(500, "Error interno del servidor"));
    }
}

