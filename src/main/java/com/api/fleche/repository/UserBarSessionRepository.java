package com.api.fleche.repository;

import com.api.fleche.model.UserBarSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserBarSessionRepository extends JpaRepository<UserBarSession, Long> {
    Optional<UserBarSession> findByUserIdAndDateExpiresAfter(Long usuarioId, LocalDateTime now);

    @Query(value = "SELECT STATUS_USER_BAR FROM USER_BAR_SESSION WHERE USER_ID = ?", nativeQuery = true)
    String findByUserId(Long userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE USER_BAR_SESSION SET STATUS_USER_BAR = ?, BAR_ID = ? WHERE USER_ID = ?", nativeQuery = true)
    void checkinOrCheckout(String status, Long barId, Long userId);

    @Query(value = "SELECT BAR_ID FROM USER_BAR_SESSION WHERE USER_ID = ?", nativeQuery = true)
    Long findByBarId(Long userId);

    @Query(value = "SELECT QR_CODE FROM TB_BARS WHERE ID = ?", nativeQuery = true)
    String qrCodeBar(Long barId);

    @Query(value = "SELECT STATUS_USER_BAR FROM USER_BAR_SESSION WHERE USUARIO_ID = ? AND STATUS_USUARIO_BAR = 'ONLINE'", nativeQuery = true)
    String verifyIfUserOnline(Long userId);

}
