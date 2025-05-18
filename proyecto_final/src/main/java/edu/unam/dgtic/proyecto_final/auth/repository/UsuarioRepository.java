package edu.unam.dgtic.proyecto_final.auth.repository;

import edu.unam.dgtic.proyecto_final.auth.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findAllByOrderByIdAsc();

    Usuario findByEmail(String email);

    boolean existsByEmail(String email);
}
