package com.api.fleche.services.Impl;

import com.api.fleche.models.Usuario;
import com.api.fleche.repositories.UsuarioRepository;
import com.api.fleche.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario criarConta(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByNumero(String numero) {
        return usuarioRepository.existsByNumero(numero);
    }

    @Override
    public boolean verificaIdade(LocalDate dataNascimento) {
        if (dataNascimento == null) {
            return false;
        }
        LocalDate hoje = LocalDate.now();
        Period idade = Period.between(dataNascimento, hoje);

        return idade.getYears() >= 18;
    }

}
