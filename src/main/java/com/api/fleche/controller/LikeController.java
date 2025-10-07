package com.api.fleche.controller;

import com.api.fleche.enums.StatusLike;
import com.api.fleche.model.Like;
import com.api.fleche.model.User;
import com.api.fleche.service.LikeService;
import com.api.fleche.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
@CrossOrigin(origins = "*")
public class LikeController {

    private final LikeService likeService;
    private final UserService userService;

    @PostMapping("/{origemId}/{destinoId}/{status}")
    public ResponseEntity<String> darLike(@PathVariable Long origemId, @PathVariable Long destinoId, @PathVariable StatusLike status) {
        Optional<User> usuarioOrigem = userService.findById(origemId);
        Optional<User> usuarioDestino = userService.findById(destinoId);

        if (usuarioOrigem == null || usuarioDestino == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }

        Like like = likeService.darLike(usuarioOrigem.get(), usuarioDestino.get(), status);

        if (status == StatusLike.LIKE && likeService.verifyMatch(usuarioOrigem.get(), usuarioDestino.get())) {
            return ResponseEntity.ok("MATCH encontrado!");
        }

        return ResponseEntity.ok("Like registrado.");
    }

}