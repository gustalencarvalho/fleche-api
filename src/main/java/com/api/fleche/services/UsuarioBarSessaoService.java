package com.api.fleche.services;

import com.api.fleche.dtos.UsuarioBarDto;
import com.api.fleche.dtos.UsuarioDto;
import com.api.fleche.models.UsuarioBarSessao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UsuarioBarSessaoService {
    boolean findByUsuarioIdAndDataExpiracaoAfter(Long usuarioId);
    String findByStatusUsuarioBar(Long usuarioId);
    void realizarCheckin(Long usuarioId, Long barId);
    void realizarCheckout(Long usuarioId, Long barId);
    Long findByBarId(Long usuarioId);
    void salvar(UsuarioBarSessao usuarioBarSessao);
    List<UsuarioBarDto> usuariosParaListar(String qrCode, Long usuarioId);
    String qrCodeBar(Long barId);
}
