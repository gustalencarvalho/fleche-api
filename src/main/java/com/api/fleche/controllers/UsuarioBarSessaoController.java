package com.api.fleche.controllers;

import com.api.fleche.dtos.UsuarioBarDto;
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
import java.util.List;

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

        if (status != null && !status.isEmpty() && status.equals("ONLINE")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Usuário já está ativo em outro bar!");
        }

        var bar = barService.findbyQrCode(usuarioBarSessaoDto.getQrCode());

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

        if (status == null) {
            usuarioBarSessaoService.salvar(usuarioBarSessaoModel);
        } else {
            usuarioBarSessaoService.realizarCheckin(usuario.getId(), bar.getId());
        }

        return ResponseEntity.status(HttpStatus.OK).body("Check-in realizado com sucesso!");
    }

    @PatchMapping("/checkout/{usuarioId}")
    public ResponseEntity<Object> checkoutUsuario(@PathVariable Long usuarioId) {
        var usuario = usuarioService.findById(usuarioId);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Usuário não encontrado na base de dados!");
        }

        var bar = usuarioBarSessaoService.findByBarId(usuarioId);
        if (bar == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Bar não encontrado na base de dados!");
        }

        usuarioBarSessaoService.realizarCheckout(usuarioId, bar);
        return ResponseEntity.status(HttpStatus.OK).body("Você agora está offline");
    }

    @GetMapping("/usuarios-online/{usuarioId}")
    public ResponseEntity<List<UsuarioBarDto>> usuariosParaListar(@PathVariable Long usuarioId) {
        var usuario = usuarioService.findById(usuarioId);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var bar = usuarioBarSessaoService.findByBarId(usuarioId);
        if (bar == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        String qrCode = usuarioBarSessaoService.qrCodeBar(bar);
        List<UsuarioBarDto> usuarioBarDtos = usuarioBarSessaoService.usuariosParaListar(qrCode, usuarioId);


        return ResponseEntity.status(HttpStatus.OK).body(usuarioBarDtos);
    }

}
