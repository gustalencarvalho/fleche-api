package com.api.fleche.repositories;

import com.api.fleche.dtos.LoginDto;
import com.api.fleche.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);
    boolean existsByTelefone(String numero);
    UserDetails findByTelefone(String telefone);

}
