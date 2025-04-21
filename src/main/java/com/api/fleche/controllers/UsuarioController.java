package com.api.fleche.controllers;

import com.api.fleche.dtos.UsuarioAtualizarDto;
import com.api.fleche.models.Usuario;
import com.api.fleche.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/{id}/dados")
    public ResponseEntity<Object> buscarDadosDoUsuario(@PathVariable Long id) {
        var usuario = usuarioService.buscarDadosUsuario(id);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Usuário não encontrado!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

    @GetMapping("/{id}/foto")
    public ResponseEntity<byte[]> getFoto(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.findById(id);
        if (usuario.isPresent() && usuario.get().getFoto() != null) {
            byte[] imagem = usuario.get().getFoto();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imagem, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/atualizar")
    public ResponseEntity<?> atualizarDados(@RequestBody UsuarioAtualizarDto atualizarDto, @RequestParam(value = "foto", required = false) MultipartFile foto, @PathVariable Long id) {
        var usuario = usuarioService.buscarDadosUsuario(id);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Usuário não encontrado!");
        }
        if (atualizarDto.getNumero() != null) {
            if (atualizarDto.getNumero().equals(usuario.getNumero())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: telefone já cadastrado no sistema.");
            }
        }
        if (atualizarDto.getEmail() != null) {
            if (atualizarDto.getEmail().equals(usuario.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: e-mail já cadastrado no sistema.");
            }
        }
        try {
            if (foto != null) {
                atualizarDto.setFoto(foto.getBytes());
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar imagem");
        }
        usuarioService.atualizarDados(atualizarDto, id);
        return ResponseEntity.status(HttpStatus.OK).body("Dados atualizados com sucesso!");
    }

}
