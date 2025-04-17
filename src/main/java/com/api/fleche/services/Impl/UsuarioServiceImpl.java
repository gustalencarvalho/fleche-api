package com.api.fleche.services.Impl;

import com.api.fleche.dao.UsuarioDao;
import com.api.fleche.dtos.LoginDto;
import com.api.fleche.dtos.UsuarioDadosDto;
import com.api.fleche.models.Usuario;
import com.api.fleche.repositories.UsuarioRepository;
import com.api.fleche.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioDao usuarioDao;

    @Transactional
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

    @Override
    public Optional<Usuario> findById(Long usuarioId) {
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
        return usuario;
    }

    @Override
    public UsuarioDadosDto buscarDadosUsuario(String numero) {
        return usuarioDao.buscarDadosUsuario(numero);
    }


    @Override
    public LoginDto findSenhaByNumero(String senha) {
        return usuarioRepository.findSenhaByNumero(senha);
    }

    @Override
    public LoginDto login(String emailOuNumero) {
        return usuarioDao.buscarDadosLogin(emailOuNumero);
    }

}
