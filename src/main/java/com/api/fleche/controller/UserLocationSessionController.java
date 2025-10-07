package com.api.fleche.controller;

import com.api.fleche.model.dtos.LocationDto;
import com.api.fleche.model.dtos.UserBarDto;
import com.api.fleche.model.dtos.UserBarSessionDto;
import com.api.fleche.enums.StatusUserLocation;
import com.api.fleche.model.UserLocationSession;
import com.api.fleche.service.LocationService;
import com.api.fleche.service.UserService;
import com.api.fleche.service.UserBarSessionService;
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
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
public class UserLocationSessionController {

    private final UserBarSessionService userBarSessionService;
    private final LocationService locationService;
    private final UserService userService;

    @PostMapping("/checkin")
    public ResponseEntity<Object> checkinUser(@RequestBody @Valid UserBarSessionDto userBarSessionDto) {
        String status = userBarSessionService.findByStatusUserBar(userBarSessionDto.getUserId());
        if (status != null && !status.isEmpty() && status.equals("ONLINE")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Usuário já está ativo em outro bar!");
        }
        var bar = locationService.findbyQrCode(userBarSessionDto.getQrCode());

        if (bar == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Bar não encontrado na base de dados!");
        }
        var usuario = userService.findById(userBarSessionDto.getUserId());

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Usuário não encontrado na base de dados!");
        }
        var usuarioBarSessaoModel = new UserLocationSession();
        usuarioBarSessaoModel.setLocation(bar);
        usuarioBarSessaoModel.setUser(usuario.get());
        usuarioBarSessaoModel.setDateActive(LocalDateTime.now(ZoneId.of("UTC")));
        usuarioBarSessaoModel.setDateExpires(LocalDateTime.now().plusHours(4));
        usuarioBarSessaoModel.setStatusUserLocation(StatusUserLocation.ONLINE);

        if (status == null) {
            userBarSessionService.save(usuarioBarSessaoModel);
        } else {
            userBarSessionService.checkin(usuario.get().getId(), bar.getId());
        }
        return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("message", "Check-in realizado!"));
    }

    @PatchMapping("/checkout/{usuarioId}")
    public ResponseEntity<Object> checkoutUser(@PathVariable Long usuarioId) {
        var usuario = userService.findById(usuarioId);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Usuário não encontrado na base de dados!");
        }
        var bar = userBarSessionService.findByBarId(usuarioId);
        if (bar == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Bar não encontrado na base de dados!");
        }
        userBarSessionService.checkout(usuarioId, bar);
        return ResponseEntity.status(HttpStatus.OK).body("Você agora está offline");
    }

    @GetMapping("/users/online/{userId}")
    public ResponseEntity<Page<UserBarDto>> usersList(@PathVariable Long userId, @RequestParam(value = "page") int page, @RequestParam(value = "size") int size) {
        var user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        var bar = userBarSessionService.findByBarId(user.get().getId());
        if (bar == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        String qrCode = userBarSessionService.qrCodeBar(bar);
        Pageable pageable = PageRequest.of(page, size);
        Page<UserBarDto> usuarioBarDtos = userBarSessionService.usuariosParaListar(qrCode, userId, pageable);
        return ResponseEntity.ok(usuarioBarDtos);
    }

    @GetMapping("/usuarios/{usuarioId}/online")
    public ResponseEntity<List<LocationDto>> usuariosOnline(@PathVariable Long usuarioId) {
        return ResponseEntity.status(HttpStatus.OK).body(userBarSessionService.listTotalUserBar(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/autenticado")
    public ResponseEntity<Object> verificaSeUsuarioEstaOnline(@PathVariable Long usuarioId) {
        return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("message", userBarSessionService.verificaSeUsuarioEstaOnline(usuarioId)));
    }

}
