package edu.unam.dgtic.proyecto_final.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UsuarioNotFoundException extends RuntimeException {
    public UsuarioNotFoundException(Long id) {
        super("Usuario con id " + id + " no encontrado");
    }
    public UsuarioNotFoundException(String email) { super("Usuario con email " + email + " ya existe"); }
}
