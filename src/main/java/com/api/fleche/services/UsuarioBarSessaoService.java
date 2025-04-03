package com.api.fleche.services;

import com.api.fleche.dtos.UsuarioBarDto;
import com.api.fleche.models.UsuarioBarSessao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioBarSessaoService {
    boolean findByUsuarioIdAndDataExpiracaoAfter(Long usuarioId);
    String findByStatusUsuarioBar(Long usuarioId);
    void realizarCheckin(Long usuarioId, Long barId);
    void realizarCheckout(Long usuarioId, Long barId);
    Long findByBarId(Long usuarioId);
    void salvar(UsuarioBarSessao usuarioBarSessao);
    Page<UsuarioBarDto> usuariosParaListar(String qrCode, Long usuarioId, Pageable pageable);
    String qrCodeBar(Long barId);
}
