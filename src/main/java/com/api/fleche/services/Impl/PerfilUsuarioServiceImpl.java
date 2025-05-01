package com.api.fleche.services.Impl;

import com.api.fleche.models.PerfilUsuario;
import com.api.fleche.models.Usuario;
import com.api.fleche.repositories.PerfilUsuarioRepository;
import com.api.fleche.services.PerfilUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PerfilUsuarioServiceImpl implements PerfilUsuarioService {

    private final PerfilUsuarioRepository perfilUsuarioRepository;

    public void salvarPerfil(PerfilUsuario perfilUsuario) {
        perfilUsuarioRepository.save(perfilUsuario);
    }

    @Override
    public Optional<PerfilUsuario> findByUsuarioId(Optional<Usuario> usuario) {
        return perfilUsuarioRepository.findByUsuarioId(usuario);
    }

}
