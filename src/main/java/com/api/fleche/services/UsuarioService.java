package com.api.fleche.services;

import com.api.fleche.models.Usuario;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface UsuarioService {
    Usuario criarConta(Usuario usuario);
    boolean existsByEmail(String email);
    boolean existsByNumero(String numero);
    boolean verificaIdade(LocalDate dataNascimento);
    Usuario findById(Long usuarioId);
}
