package edu.unam.dgtic.proyecto_final.auth.service;

import edu.unam.dgtic.proyecto_final.auth.dto.UsuarioDTO;
import edu.unam.dgtic.proyecto_final.auth.exception.UsuarioNotFoundException;

import java.util.List;

public interface UsuarioService {
    List<UsuarioDTO> findAll();
    UsuarioDTO findById(Long id) throws UsuarioNotFoundException;
    UsuarioDTO save(UsuarioDTO userAdmin) throws UsuarioNotFoundException;
    UsuarioDTO findByEmail(String email) throws UsuarioNotFoundException;
}
