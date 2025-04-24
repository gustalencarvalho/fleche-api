package com.api.fleche.controllers;

import com.api.fleche.dtos.PerfilUsuarioDto;
import com.api.fleche.models.PerfilUsuario;
import com.api.fleche.services.PerfilUsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/perfil")
@CrossOrigin(origins = "*")
public class PerfilUsuarioController {

    private final PerfilUsuarioService perfilUsuarioService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Void> cadastrarPerfil(@RequestBody @Valid PerfilUsuarioDto perfilUsuarioDto) {
        var perfilUsuario = new PerfilUsuario();
        BeanUtils.copyProperties(perfilUsuario, perfilUsuario);
        perfilUsuarioService.salvarPerfil(perfilUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
