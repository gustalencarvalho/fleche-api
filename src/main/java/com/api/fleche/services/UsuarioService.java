package com.api.fleche.services;

import com.api.fleche.dtos.UsuarioDadosDto;
import com.api.fleche.models.Usuario;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public interface UsuarioService {
    Usuario criarConta(Usuario usuario);
    boolean existsByEmail(String email);
    boolean existsByNumero(String numero);
    boolean verificaIdade(LocalDate dataNascimento);
    Optional<Usuario> findById(Long usuarioId);
    UsuarioDadosDto buscarDadosUsuario(String numero);
}
