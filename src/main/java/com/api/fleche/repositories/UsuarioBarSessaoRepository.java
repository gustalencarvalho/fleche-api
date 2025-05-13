package com.api.fleche.repositories;

import com.api.fleche.dtos.UsuarioDto;
import com.api.fleche.models.UsuarioBarSessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UsuarioBarSessaoRepository extends JpaRepository<UsuarioBarSessao, Long> {
    Optional<UsuarioBarSessao> findByUsuarioIdAndDataExpiracaoAfter(Long usuarioId, LocalDateTime now);

    @Query(value = "SELECT STATUS_USUARIO_BAR FROM USUARIO_BAR_SESSAO WHERE USUARIO_ID = ?", nativeQuery = true)
    String findByUsuarioId(Long usuarioId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE USUARIO_BAR_SESSAO SET STATUS_USUARIO_BAR = ?, BAR_ID = ? WHERE USUARIO_ID = ?", nativeQuery = true)
    void realizarCheckinOuCheckout(String status, Long barId, Long usuarioId);

    @Query(value = "SELECT BAR_ID FROM USUARIO_BAR_SESSAO WHERE USUARIO_ID = ?", nativeQuery = true)
    Long findByBarId(Long usuarioId);

    @Query(value = "SELECT QR_CODE FROM TB_BARES WHERE ID = ?", nativeQuery = true)
    String qrCodeBar(Long barId);

    @Query(value = "SELECT STATUS_USUARIO_BAR FROM USUARIO_BAR_SESSAO WHERE USUARIO_ID = ? AND STATUS_USUARIO_BAR = 'ONLINE'", nativeQuery = true)
    String verificaSeUsuarioEstaOnline(Long usuarioId);

}
