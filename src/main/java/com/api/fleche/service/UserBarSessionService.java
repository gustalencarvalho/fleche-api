package com.api.fleche.service;

import com.api.fleche.dao.UserLocationSessionDao;
import com.api.fleche.model.dtos.LocationDto;
import com.api.fleche.model.dtos.UserBarDto;
import com.api.fleche.enums.StatusUserLocation;
import com.api.fleche.model.UserLocationSession;
import com.api.fleche.repository.UserLocationSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserBarSessionService {

    private final UserLocationSessionRepository userLocationSessionRepository;
    private final UserLocationSessionDao userLocationSessionDao;

    public boolean findByUserIdAndDataExpireAfter(Long usuarioId) {
        return userLocationSessionRepository.findByUserIdAndDateExpiresAfter(usuarioId, LocalDateTime.now()).isPresent();
    }

    public String findByStatusUserBar(Long userId) {
        String status = userLocationSessionRepository.findByUserId(userId);
        return status;
    }

    public void checkin(Long userId, Long barId) {
        userLocationSessionRepository.checkinOrCheckout(StatusUserLocation.ONLINE.name(), barId, userId);
    }

    public void checkout(Long userId, Long barId) {
        userLocationSessionRepository.checkinOrCheckout(StatusUserLocation.OFFLINE.name(), barId,  userId);
    }

    public Long findByBarId(Long userId) {
        return userLocationSessionRepository.findByBarId(userId);
    }

    public void save(UserLocationSession userLocationSession) {
        userLocationSessionRepository.save(userLocationSession);
    }

    public String qrCodeBar(Long barId) {
        return userLocationSessionRepository.qrCodeBar(barId);
    }

    public List<LocationDto> listTotalUserBar(Long userId) {
        return userLocationSessionDao.listarTotalUsuariosPorBar(userId);
    }

    public Page<UserBarDto> usuariosParaListar(String qrCode, Long userId, Pageable pageable) {
        return userLocationSessionDao.usuariosParaListar(qrCode, userId, pageable);
    }

    public String verificaSeUsuarioEstaOnline(Long userId) {
        return userLocationSessionRepository.verifyIfUserOnline(userId);
    }
}
