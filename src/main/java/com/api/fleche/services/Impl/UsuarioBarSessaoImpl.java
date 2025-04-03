package com.api.fleche.services.Impl;

import com.api.fleche.enums.StatusUsuarioBar;
import com.api.fleche.repositories.UsuarioBarSessaoRepository;
import com.api.fleche.services.UsuarioBarSessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UsuarioBarSessaoImpl implements UsuarioBarSessaoService {

    @Autowired
    private UsuarioBarSessaoRepository usuarioBarSessaoRepository;


    public boolean findByUsuarioIdAndDataExpiracaoAfter(Long usuarioId) {
        return usuarioBarSessaoRepository.findByUsuarioIdAndDataExpiracaoAfter(usuarioId, LocalDateTime.now()).isPresent();
    }

    @Override
    public String findByStatusUsuarioBar(Long usuarioId) {
        String status = usuarioBarSessaoRepository.findByUsuarioId(usuarioId);
        return status == "ONLINE" ? status : "OFFLINE";
    }

    @Override
    public void realizarCheckin(Long usuarioId, Long barId) {
        usuarioBarSessaoRepository.realizarCheckinOuCheckout(StatusUsuarioBar.ONLINE.name(), barId, usuarioId);
    }

    @Override
    public void realizarCheckout(Long usuarioId, Long barId) {
        usuarioBarSessaoRepository.realizarCheckinOuCheckout(StatusUsuarioBar.OFFLINE.name(), barId,  usuarioId);
    }

}
