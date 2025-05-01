package com.api.fleche.repositories;

import com.api.fleche.models.PerfilUsuario;
import com.api.fleche.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfilUsuarioRepository extends JpaRepository<PerfilUsuario, Long> {
    Optional<PerfilUsuario> findByUsuarioId(Optional<Usuario> usuario);
}
