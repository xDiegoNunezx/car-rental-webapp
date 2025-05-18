package edu.unam.dgtic.proyecto_final.security.service;

import edu.unam.dgtic.proyecto_final.auth.model.Usuario;
import edu.unam.dgtic.proyecto_final.auth.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Security - UserDetailsServiceImpl.loadUserByUsername {}", username);
        Usuario usuario = Optional.ofNullable(usuarioRepository.findByEmail(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found in database"));

        String userName = usuario.getEmail();
        String password = usuario.getContrasena();

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(
                usuario.isEsAdministrador() ? "ADMIN" : "USER"
        ));

        return new User(userName, password, authorities);
    }
}