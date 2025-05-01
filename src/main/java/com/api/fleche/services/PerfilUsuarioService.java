package com.api.fleche.services;

import com.api.fleche.models.PerfilUsuario;
import com.api.fleche.models.Usuario;

import java.util.Optional;

public interface PerfilUsuarioService {
    void salvarPerfil(PerfilUsuario perfilUsuario);

    Optional<PerfilUsuario> findByUsuarioId(Optional<Usuario> usuario);
}
