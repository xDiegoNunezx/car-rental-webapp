package edu.unam.dgtic.proyecto_final.system.service;

import edu.unam.dgtic.proyecto_final.system.model.Cliente;

import java.util.Optional;

public interface ClienteService {
    Cliente guardar(Cliente cliente);
    Optional<Cliente> obtenerPorEmail(String email);
    Optional<Cliente> obtenerPorRfc(String rfc);
    Optional<Cliente> obtenerPorId(Long id);
}
