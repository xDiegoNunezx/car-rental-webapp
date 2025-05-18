package edu.unam.dgtic.proyecto_final.security.model;

import edu.unam.dgtic.proyecto_final.auth.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String name;
    private String email;
    private boolean isAdmin;
    private Collection<SimpleGrantedAuthority> authorities;
    private Map<String, Object> attributes;
    private Usuario usuario;

    public UserDetailsImpl(Usuario usuario) {
        this.usuario = usuario;
    }

    public UserDetailsImpl(Long id, String name, String email, Collection<SimpleGrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(Usuario usuario) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(
                usuario.isEsAdministrador() ? "ADMIN" : "USER"
        ));

        return new UserDetailsImpl(
                usuario.getId(),
                usuario.getNombreCompleto(),
                usuario.getEmail(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
        if (isAdmin) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("USER"));
        }
        return grantedAuthorities;
    }

    /**
     * getUsername
     * @return username
     */
    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    /**
     * getPassword (OTP)
     * @return password
     */
    @Override
    public String getPassword() {
        return usuario.getContrasena();
    }

    /**
     * getName
     * @return name
     */
    public String getName() {
        return usuario.getNombreCompleto();
    }

    /**
     * getEmail
     * @return email
     */
    public String getEmail() {
        return usuario.getEmail();
    }

    /**
     * isEnabled
     * @return if user is enabled
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * isAccountNonLocked
     * @return if user is locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * isAccountNonExpired
     * @return if account is not expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * isCredentialsNonExpired
     * @return if credential is not expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
