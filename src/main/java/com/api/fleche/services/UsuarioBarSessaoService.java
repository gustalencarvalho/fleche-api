package com.api.fleche.services;

import com.api.fleche.models.UsuarioBarSessao;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UsuarioBarSessaoService {
    boolean findByUsuarioIdAndDataExpiracaoAfter(Long usuarioId);
    String findByStatusUsuarioBar(Long usuarioId);
    void realizarCheckin(UsuarioBarSessao usuarioBarSessao);
}
