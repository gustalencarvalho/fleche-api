package com.api.fleche.controllers;

import com.api.fleche.dtos.BaresDto;
import com.api.fleche.dtos.UsuarioBarDto;
import com.api.fleche.dtos.UsuarioBarSessaoDto;
import com.api.fleche.enums.StatusUsuarioBar;
import com.api.fleche.models.UsuarioBarSessao;
import com.api.fleche.services.BarService;
import com.api.fleche.services.UsuarioBarSessaoService;
import com.api.fleche.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/sessao")
@RequiredArgsConstructor
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
        usuarioBarSessaoModel.setUsuario(usuario.get());
        usuarioBarSessaoModel.setDataAtivacao(LocalDateTime.now(ZoneId.of("UTC")));
        usuarioBarSessaoModel.setDataExpiracao(LocalDateTime.now().plusHours(4));
        usuarioBarSessaoModel.setStatusUsuarioBar(StatusUsuarioBar.ONLINE);

        if (status == null) {
            usuarioBarSessaoService.salvar(usuarioBarSessaoModel);
        } else {
            usuarioBarSessaoService.realizarCheckin(usuario.get().getId(), bar.getId());
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

    @GetMapping("/usuarios/disponiveis/{usuarioId}")
    public ResponseEntity<Page<UsuarioBarDto>> usuariosParaListar(@PathVariable Long usuarioId, @RequestParam(value = "page") int page, @RequestParam(value = "size") int size) {
        var usuario = usuarioService.findById(usuarioId);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        var bar = usuarioBarSessaoService.findByBarId(usuario.get().getId());
        if (bar == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        String qrCode = usuarioBarSessaoService.qrCodeBar(bar);
        Pageable pageable = PageRequest.of(page, size);
        Page<UsuarioBarDto> usuarioBarDtos = usuarioBarSessaoService.usuariosParaListar(qrCode, usuarioId, pageable);
        return ResponseEntity.ok(usuarioBarDtos);
    }

    @GetMapping("/usuarios/{usuarioId}/online")
    public ResponseEntity<List<BaresDto>> usuariosOnline(@PathVariable Long usuarioId) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioBarSessaoService.listarTotalUsuariosPorBar(usuarioId));
    }

}
