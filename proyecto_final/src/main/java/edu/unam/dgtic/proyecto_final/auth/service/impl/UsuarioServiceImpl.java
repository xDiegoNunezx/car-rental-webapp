package edu.unam.dgtic.proyecto_final.auth.service.impl;

import edu.unam.dgtic.proyecto_final.auth.dto.UsuarioDTO;
import edu.unam.dgtic.proyecto_final.auth.exception.UsuarioNotFoundException;
import edu.unam.dgtic.proyecto_final.auth.model.Usuario;
import edu.unam.dgtic.proyecto_final.auth.repository.UsuarioRepository;
import edu.unam.dgtic.proyecto_final.auth.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UsuarioDTO> findAll() {
        log.info("Service - UsuarioServiceImpl.findAll");
        List<Usuario> theList = usuarioRepository.findAllByOrderByIdAsc();
        return theList.stream().map(this::convertEntityToDTO).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public UsuarioDTO findById(Long id) throws UsuarioNotFoundException {
        log.info("Service - UsuarioServiceImpl.findById {}", id);
        Usuario object = usuarioRepository.findById(id).orElseThrow(() ->
                new UsuarioNotFoundException(id));
        return convertEntityToDTO(object);
    }

    @Override
    public UsuarioDTO save(UsuarioDTO userAdmin) throws UsuarioNotFoundException {
        log.info("Service - UserAdmin.save");
        log.info("Service - UserAdmin.save object {} ", userAdmin);

        if (existsByEmail(userAdmin.getEmail()))
            throw new UsuarioNotFoundException(userAdmin.getEmail());

        userAdmin.setContrasena(passwordEncoder.encode(userAdmin.getContrasena()));
        Usuario finalStatus = convertDTOtoEntity(userAdmin);
        finalStatus = usuarioRepository.save(finalStatus);

        return convertEntityToDTO(finalStatus);
    }

    @Override
    public UsuarioDTO findByEmail(String email) throws UsuarioNotFoundException {
        Usuario object = usuarioRepository.findByEmail(email);

        if(object == null)
            throw new UsuarioNotFoundException(email);

        return convertEntityToDTO(object);
    }

    private boolean existsByEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    private UsuarioDTO convertEntityToDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setRfc(usuario.getRfc());
        dto.setEmail(usuario.getEmail());
        dto.setNombreCompleto(usuario.getNombreCompleto());
        dto.setNumeroTelefono(usuario.getNumeroTelefono());
        dto.setFechaNacimiento(usuario.getFechaNacimiento());
        dto.setContrasena(usuario.getContrasena());
        dto.setDireccion(usuario.getDireccion());
        dto.setPais(usuario.getPais());
        dto.setCiudad(usuario.getCiudad());
        dto.setEsAdministrador(usuario.isEsAdministrador());

        return dto;
    }

    private Usuario convertDTOtoEntity(UsuarioDTO usuario) {
        Usuario entity = new Usuario();
        entity.setId(usuario.getId());
        entity.setRfc(usuario.getRfc());
        entity.setEmail(usuario.getEmail());
        entity.setNombreCompleto(usuario.getNombreCompleto());
        entity.setNumeroTelefono(usuario.getNumeroTelefono());
        entity.setFechaNacimiento(usuario.getFechaNacimiento());
        entity.setContrasena(usuario.getContrasena());
        entity.setDireccion(usuario.getDireccion());
        entity.setPais(usuario.getPais());
        entity.setEsAdministrador(usuario.getEsAdministrador());

        return entity;
    }
}
