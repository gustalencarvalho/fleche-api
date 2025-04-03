package com.api.fleche.services;

import com.api.fleche.models.UsuarioBarSessao;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UsuarioBarSessaoService {
    boolean findByUsuarioIdAndDataExpiracaoAfter(Long usuarioId);
    String findByStatusUsuarioBar(Long usuarioId);
    void realizarCheckin(Long usuarioId, Long barId);
    void realizarCheckout(Long usuarioId, Long barId);
    Long findByBarId(Long usuarioId);
}
