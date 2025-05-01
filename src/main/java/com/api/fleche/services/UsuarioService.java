package com.api.fleche.services;

import com.api.fleche.dtos.LoginDto;
import com.api.fleche.dtos.PerfilUsuarioDto;
import com.api.fleche.dtos.UsuarioDadosDto;
import com.api.fleche.models.Usuario;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public interface UsuarioService {
    Usuario criarConta(Usuario usuario);

    boolean existsByEmail(String email);

    boolean existsByTelefone(String numero);

    boolean verificaIdade(LocalDate dataNascimento);

    Optional<Usuario> findById(Long usuarioId);

    UsuarioDadosDto buscarDadosUsuario(Long id);

    LoginDto login(String emailOuNumero);

    void atualizarDados(UsuarioDadosDto atualizarDto, Long id);

    UserDetails findUsuarioSemPerfil(String telefone);

    PerfilUsuarioDto perfilUsuario(Long usuarioId);

    UserDetails findByTelefone(String telefone);

}
