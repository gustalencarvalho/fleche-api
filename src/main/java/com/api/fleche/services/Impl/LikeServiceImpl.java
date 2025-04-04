package com.api.fleche.services.Impl;

import com.api.fleche.enums.StatusLike;
import com.api.fleche.models.Like;
import com.api.fleche.models.Usuario;
import com.api.fleche.repositories.LikeRepository;
import com.api.fleche.services.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    @Override
    public Like darLike(Usuario usuarioOrigem, Usuario usuarioDestino, StatusLike statusLike) {
        Optional<Like> likeExistente = likeRepository.findByUsuarioOrigemAndUsuarioDestino(usuarioOrigem, usuarioDestino);

        if (likeExistente.isPresent()) {
            Like like = likeExistente.get();
            like.setStatus(statusLike);
            return likeRepository.save(like);
        }

        Like novoLike = new Like(usuarioOrigem, usuarioDestino, statusLike);
        return likeRepository.save(novoLike);
    }

    @Override
    public boolean verificarMatch(Usuario usuarioOrigem, Usuario usuarioDestino) {
        return likeRepository.existsByUsuarioOrigemAndUsuarioDestinoAndStatus(usuarioOrigem, usuarioDestino, StatusLike.LIKE) && likeRepository.existsByUsuarioOrigemAndUsuarioDestinoAndStatus(usuarioDestino, usuarioOrigem, StatusLike.LIKE);
    }

}
