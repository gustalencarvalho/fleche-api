package com.api.fleche.controllers;

import com.api.fleche.dtos.UsuarioBarSessaoDto;
import com.api.fleche.enums.StatusUsuarioBar;
import com.api.fleche.models.UsuarioBarSessao;
import com.api.fleche.services.BarService;
import com.api.fleche.services.UsuarioBarSessaoService;
import com.api.fleche.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("/sessao")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class UsuarioBarSessaoController {

    private final UsuarioBarSessaoService usuarioBarSessaoService;
    private final BarService barService;
    private final UsuarioService usuarioService;

    @PostMapping("/checkin")
    public ResponseEntity<Object> checkinUsuario(@RequestBody @Valid UsuarioBarSessaoDto usuarioBarSessaoDto) {
        String status = usuarioBarSessaoService.findByStatusUsuarioBar(usuarioBarSessaoDto.getUsuarioId());

        if (status.equals("ONLINE") && !status.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Usuário já está ativo em outro bar!");
        }

        var bar = barService.findbyId(usuarioBarSessaoDto.getBarId());

        if (bar == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Bar não encontrado na base de dados!");
        }

        var usuario = usuarioService.findById(usuarioBarSessaoDto.getUsuarioId());

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Usuário não encontrado na base de dados!");
        }

        var usuarioBarSessaoModel = new UsuarioBarSessao();
        usuarioBarSessaoModel.setBar(bar);
        usuarioBarSessaoModel.setUsuario(usuario);
        usuarioBarSessaoModel.setDataAtivacao(LocalDateTime.now(ZoneId.of("UTC")));
        usuarioBarSessaoModel.setDataExpiracao(LocalDateTime.now().plusHours(4));
        usuarioBarSessaoModel.setStatusUsuarioBar(StatusUsuarioBar.ONLINE);
        usuarioBarSessaoService.realizarCheckin(usuarioBarSessaoModel);

        return ResponseEntity.status(HttpStatus.OK).body("Checkin realizado com sucesso!");
    }

    @PatchMapping("/checkout/{usuarioId}")
    public ResponseEntity<Object> checkoutUsuario(@PathVariable Long usuarioId) {
        var usuario = usuarioService.findById(usuarioId);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Usuário não encontrado na base de dados!");
        }

        usuarioBarSessaoService.realizarCheckout(usuarioId);
        return ResponseEntity.status(HttpStatus.OK).body("Você agora está offline");
    }
}
