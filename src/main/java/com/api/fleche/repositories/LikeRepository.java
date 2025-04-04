package com.api.fleche.repositories;

import com.api.fleche.enums.StatusLike;
import com.api.fleche.models.Like;
import com.api.fleche.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    // Busca se já existe um like entre os usuários
    Optional<Like> findByUsuarioOrigemAndUsuarioDestino(Usuario usuarioOrigem, Usuario usuarioDestino);

    // Lista todos os likes que um usuário deu
    List<Like> findByUsuarioOrigem(Usuario usuarioOrigem);

    // Lista todos os likes que um usuário recebeu
    List<Like> findByUsuarioDestino(Usuario usuarioDestino);

    // Verifica se há um match entre dois usuários
    boolean existsByUsuarioOrigemAndUsuarioDestinoAndStatus(Usuario usuarioOrigem, Usuario usuarioDestino, StatusLike statusLike);

}
