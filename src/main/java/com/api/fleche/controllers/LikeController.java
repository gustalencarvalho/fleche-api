package com.api.fleche.controllers;

import com.api.fleche.enums.StatusLike;
import com.api.fleche.models.Like;
import com.api.fleche.models.Usuario;
import com.api.fleche.services.LikeService;
import com.api.fleche.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;
    private final UsuarioService usuarioService;

    @PostMapping("/{origemId}/{destinoId}/{status}")
    public ResponseEntity<String> darLike(@PathVariable Long origemId, @PathVariable Long destinoId, @PathVariable StatusLike status) {
        Optional<Usuario> usuarioOrigem = usuarioService.findById(origemId);
        Optional<Usuario> usuarioDestino = usuarioService.findById(destinoId);

        if (usuarioOrigem == null || usuarioDestino == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }

        Like like = likeService.darLike(usuarioOrigem.get(), usuarioDestino.get(), status);

        if (status == StatusLike.LIKE && likeService.verificarMatch(usuarioOrigem.get(), usuarioDestino.get())) {
            return ResponseEntity.ok("MATCH encontrado! 🎉");
        }

        return ResponseEntity.ok("Like registrado.");
    }

}