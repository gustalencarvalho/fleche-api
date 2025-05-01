package com.api.fleche.controllers;

import com.api.fleche.dtos.PerfilUsuarioDto;
import com.api.fleche.models.PerfilUsuario;
import com.api.fleche.services.PerfilUsuarioService;
import com.api.fleche.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/perfil")
public class PerfilUsuarioController {

    private final PerfilUsuarioService perfilUsuarioService;
    private final UsuarioService usuarioService;

    @GetMapping("/{id}/foto")
    public ResponseEntity<byte[]> getFoto(@PathVariable Long id) {
        var usuario = usuarioService.findById(id);
        Optional<PerfilUsuario> perfilUsuario = perfilUsuarioService.findByUsuarioId(usuario);
        if (perfilUsuario.isPresent() && perfilUsuario.get().getFoto() != null) {
            byte[] imagem = perfilUsuario.get().getFoto();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imagem, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Void> cadastrarPerfil(
            @RequestPart("perfil") @Valid PerfilUsuarioDto perfilUsuarioDto,
            @RequestPart(value = "foto", required = false) MultipartFile foto) {
        try {
            var usuario = usuarioService.findById(perfilUsuarioDto.getId());
            var perfilUsuario = new PerfilUsuario();

            perfilUsuario.setGenero(perfilUsuarioDto.getGenero().name());
            perfilUsuario.setBio(perfilUsuarioDto.getBio());
            perfilUsuario.setPreferencia(perfilUsuarioDto.getPreferencia().name());

            if (foto != null && !foto.isEmpty()) {
                perfilUsuario.setFoto(foto.getBytes());
            }

            perfilUsuario.setUsuarioId(usuario.get());

            perfilUsuarioService.salvarPerfil(perfilUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
