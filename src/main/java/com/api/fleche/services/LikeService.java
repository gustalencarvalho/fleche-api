package com.api.fleche.services;

import com.api.fleche.enums.StatusLike;
import com.api.fleche.models.Like;
import com.api.fleche.models.Usuario;

public interface LikeService {
    Like darLike(Usuario usuarioOrigem, Usuario usuarioDestino, StatusLike statusLike);
    boolean verificarMatch(Usuario usuarioOrigem, Usuario usuarioDestino);
}
