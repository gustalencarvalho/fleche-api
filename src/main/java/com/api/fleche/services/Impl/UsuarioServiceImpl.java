package com.api.fleche.services.Impl;

import com.api.fleche.dao.UsuarioDao;
import com.api.fleche.dtos.LoginDto;
import com.api.fleche.dtos.UsuarioAtualizarDto;
import com.api.fleche.dtos.UsuarioDadosDto;
import com.api.fleche.models.Usuario;
import com.api.fleche.repositories.UsuarioRepository;
import com.api.fleche.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.Modifying;
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
    public UsuarioDadosDto buscarDadosUsuario(Long id) {
        return usuarioDao.buscarDadosUsuario(id);
    }


    @Override
    public LoginDto findSenhaByNumero(String senha) {
        return usuarioRepository.findSenhaByNumero(senha);
    }

    @Override
    public LoginDto login(String emailOuNumero) {
        return usuarioDao.buscarDadosLogin(emailOuNumero);
    }

    @Transactional
    @Modifying
    @Override
    public void atualizarDados(UsuarioAtualizarDto atualizarDto, Long id) {
        Optional<Usuario> usuario = findById(id);
        usuario.get().setNome(atualizarDto.getNome() != null ? atualizarDto.getNome() :  usuario.get().getNome());
        usuario.get().setNumero(atualizarDto.getNumero() != null ? atualizarDto.getNumero() : usuario.get().getNumero());
        usuario.get().setEmail(atualizarDto.getEmail() != null ? atualizarDto.getEmail() : usuario.get().getEmail());
        usuario.get().setGenero(atualizarDto.getGenero() != null ? atualizarDto.getGenero() : usuario.get().getGenero());
        usuario.get().setPreferencia(atualizarDto.getPreferencia() != null ? atualizarDto.getPreferencia() : usuario.get().getPreferencia());
        usuario.get().setDataNascimento(usuario.get().getDataNascimento());
        usuario.get().setSenha(usuario.get().getSenha());
        usuario.get().setFoto(atualizarDto.getFoto() != null ? atualizarDto.getFoto() : usuario.get().getFoto());
        usuarioRepository.save(usuario.get());
    }

}
