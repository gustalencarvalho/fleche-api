package com.api.fleche.repositories;

import com.api.fleche.dtos.UsuarioDadosDto;
import com.api.fleche.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);
    boolean existsByNumero(String numero);
}
