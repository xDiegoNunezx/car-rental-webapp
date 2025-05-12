package edu.unam.dgtic.proyecto_final.system.service;

import edu.unam.dgtic.proyecto_final.system.model.Usuario;

import java.util.Optional;

public interface UsuarioService {
    Usuario guardar(Usuario usuario);
    Optional<Usuario> obtenerPorEmail(String email);
    Optional<Usuario> obtenerPorRfc(String rfc);
    Optional<Usuario> obtenerPorId(Long id);
}
