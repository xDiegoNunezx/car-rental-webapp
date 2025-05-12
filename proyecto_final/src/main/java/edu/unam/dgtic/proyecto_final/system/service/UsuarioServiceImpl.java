package edu.unam.dgtic.proyecto_final.system.service;


import edu.unam.dgtic.proyecto_final.system.model.Usuario;
import edu.unam.dgtic.proyecto_final.system.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new IllegalStateException("El email ya está registrado");
        }

        if (usuarioRepository.findByRfc(usuario.getRfc()).isPresent()) {
            throw new IllegalStateException("El RFC ya está registrado");
        }

        // Validar edad mínima (18 años)
        Period edad = Period.between(usuario.getFechaNacimiento(), LocalDate.now());
        if (edad.getYears() < 18) {
            throw new IllegalStateException("Debes tener al menos 18 años para registrarte");
        }

        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public Optional<Usuario> obtenerPorRfc(String email) {
        return usuarioRepository.findByRfc(email);
    }

    @Override
    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }
}
