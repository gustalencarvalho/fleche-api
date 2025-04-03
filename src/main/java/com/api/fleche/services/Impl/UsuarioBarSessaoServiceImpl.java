package com.api.fleche.services.Impl;

import com.api.fleche.dao.UsuarioBaSessaoDao;
import com.api.fleche.dtos.UsuarioBarDto;
import com.api.fleche.enums.StatusUsuarioBar;
import com.api.fleche.models.UsuarioBarSessao;
import com.api.fleche.repositories.UsuarioBarSessaoRepository;
import com.api.fleche.services.UsuarioBarSessaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UsuarioBarSessaoServiceImpl implements UsuarioBarSessaoService {

    private final UsuarioBarSessaoRepository usuarioBarSessaoRepository;
    private final UsuarioBaSessaoDao usuarioBaSessaoDao;


    public boolean findByUsuarioIdAndDataExpiracaoAfter(Long usuarioId) {
        return usuarioBarSessaoRepository.findByUsuarioIdAndDataExpiracaoAfter(usuarioId, LocalDateTime.now()).isPresent();
    }

    @Override
    public String findByStatusUsuarioBar(Long usuarioId) {
        String status = usuarioBarSessaoRepository.findByUsuarioId(usuarioId);
        return status;
    }

    @Override
    public void realizarCheckin(Long usuarioId, Long barId) {
        usuarioBarSessaoRepository.realizarCheckinOuCheckout(StatusUsuarioBar.ONLINE.name(), barId, usuarioId);
    }

    @Override
    public void realizarCheckout(Long usuarioId, Long barId) {
        usuarioBarSessaoRepository.realizarCheckinOuCheckout(StatusUsuarioBar.OFFLINE.name(), barId,  usuarioId);
    }

    @Override
    public Long findByBarId(Long usuarioId) {
        return usuarioBarSessaoRepository.findByBarId(usuarioId);
    }

    public void salvar(UsuarioBarSessao usuarioBarSessao) {
        usuarioBarSessaoRepository.save(usuarioBarSessao);
    }

    public String qrCodeBar(Long barId) {
        return usuarioBarSessaoRepository.qrCodeBar(barId);
    }

    @Override
    public Page<UsuarioBarDto> usuariosParaListar(String qrCode, Long usuarioId, Pageable pageable) {
        return usuarioBaSessaoDao.usuariosParaListar(qrCode, usuarioId, pageable);
    }

}
