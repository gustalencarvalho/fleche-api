package com.api.fleche.services.Impl;

import com.api.fleche.models.PerfilUsuario;
import com.api.fleche.repositories.PerfilUsuarioRepository;
import com.api.fleche.services.PerfilUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PerfilUsuarioServiceImpl implements PerfilUsuarioService {

    private final PerfilUsuarioRepository perfilUsuarioRepository;

    public void salvarPerfil(PerfilUsuario perfilUsuario) {
        perfilUsuarioRepository.save(perfilUsuario);
    }

}
