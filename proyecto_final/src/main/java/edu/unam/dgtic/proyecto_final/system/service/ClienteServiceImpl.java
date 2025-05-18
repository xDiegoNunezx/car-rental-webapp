package edu.unam.dgtic.proyecto_final.system.service;

import edu.unam.dgtic.proyecto_final.system.model.Cliente;
import edu.unam.dgtic.proyecto_final.system.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Slf4j
@Service
public class ClienteServiceImpl implements ClienteService {
   @Autowired
   private ClienteRepository clienteRepository;

    public String getText() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Auth {}", auth);
        log.info("Credentials {}", auth.getCredentials());
        return "User";
    }

    @Override
    @Transactional
    public Cliente guardar(Cliente cliente) {
        if (clienteRepository.findByEmail(cliente.getEmail()).isPresent()) {
            throw new IllegalStateException("El email ya está registrado");
        }

        if (clienteRepository.findByRfc(cliente.getRfc()).isPresent()) {
            throw new IllegalStateException("El RFC ya está registrado");
        }

        // Validar edad mínima (18 años)
        Period edad = Period.between(cliente.getFechaNacimiento(), LocalDate.now());
        if (edad.getYears() < 18) {
            throw new IllegalStateException("Debes tener al menos 18 años para registrarte");
        }

        return clienteRepository.save(cliente);
    }

    @Override
    public Optional<Cliente> obtenerPorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    @Override
    public Optional<Cliente> obtenerPorRfc(String rfc) {
        return clienteRepository.findByRfc(rfc);
    }

    @Override
    public Optional<Cliente> obtenerPorId(Long id) {
        return clienteRepository.findById(id);
    }
}
