package com.api.fleche.repositories;

import com.api.fleche.dtos.LoginDto;
import com.api.fleche.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);
    boolean existsByTelefone(String numero);
    @Query("SELECT new com.api.fleche.models.Usuario(u.id, u.nome, u.email, u.ddd, u.telefone, u.dataNascimento, u.senha, u.role) " +
            "FROM Usuario u WHERE u.telefone = :telefone")
    Optional<Usuario> findUsuarioSemPerfil(@Param("telefone") String telefone);
    UserDetails findByTelefone(String telefone);
}
