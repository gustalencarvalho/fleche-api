package com.api.fleche.repositories;

import com.api.fleche.models.UsuarioBarSessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UsuarioBarSessaoRepository extends JpaRepository<UsuarioBarSessao, Long> {
    Optional<UsuarioBarSessao> findByUsuarioIdAndDataExpiracaoAfter(Long usuarioId, LocalDateTime now);

    @Query(value = "SELECT STATUS_USUARIO_BAR FROM USUARIO_BAR_SESSAO WHERE USUARIO_ID = ?", nativeQuery = true)
    String findByUsuarioId(Long usuarioId);
}
