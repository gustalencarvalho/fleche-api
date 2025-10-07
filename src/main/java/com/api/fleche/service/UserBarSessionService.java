package com.api.fleche.service;

import com.api.fleche.dao.UserBarSessionDao;
import com.api.fleche.model.dtos.BarsDto;
import com.api.fleche.model.dtos.UserBarDto;
import com.api.fleche.enums.StatusUserBar;
import com.api.fleche.model.UserBarSession;
import com.api.fleche.repository.UserBarSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserBarSessionService {

    private final UserBarSessionRepository userBarSessionRepository;
    private final UserBarSessionDao userBarSessionDao;


    public boolean findByUserIdAndDataExpireAfter(Long usuarioId) {
        return userBarSessionRepository.findByUserIdAndDateExpiresAfter(usuarioId, LocalDateTime.now()).isPresent();
    }

    public String findByStatusUserBar(Long userId) {
        String status = userBarSessionRepository.findByUserId(userId);
        return status;
    }

    public void checkin(Long userId, Long barId) {
        userBarSessionRepository.checkinOrCheckout(StatusUserBar.ONLINE.name(), barId, userId);
    }

    public void checkout(Long userId, Long barId) {
        userBarSessionRepository.checkinOrCheckout(StatusUserBar.OFFLINE.name(), barId,  userId);
    }

    public Long findByBarId(Long userId) {
        return userBarSessionRepository.findByBarId(userId);
    }

    public void save(UserBarSession userBarSession) {
        userBarSessionRepository.save(userBarSession);
    }

    public String qrCodeBar(Long barId) {
        return userBarSessionRepository.qrCodeBar(barId);
    }

    public List<BarsDto> listTotalUserBar(Long userId) {
        return userBarSessionDao.listarTotalUsuariosPorBar(userId);
    }

    public Page<UserBarDto> usuariosParaListar(String qrCode, Long userId, Pageable pageable) {
        return userBarSessionDao.usuariosParaListar(qrCode, userId, pageable);
    }

    public String verificaSeUsuarioEstaOnline(Long userId) {
        return userBarSessionRepository.verifyIfUserOnline(userId);
    }
}
