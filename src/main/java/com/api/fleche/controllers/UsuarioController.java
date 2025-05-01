package com.api.fleche.controllers;

import com.api.fleche.models.PerfilUsuario;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
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

    @PatchMapping("/{id}/atualizar")
    public ResponseEntity<?> atualizarDados(@RequestParam(value = "nome", required = false) String nome,
                                            @RequestParam(value = "email", required = false) String email,
                                            @RequestParam(value = "numero", required = false) String numero,
                                            @RequestParam(value = "genero", required = false) String genero,
                                            @RequestParam(value = "preferencia", required = false) String preferencia,
                                            @RequestParam(value = "foto", required = false) MultipartFile foto,
                                            @PathVariable Long id) {
        var usuario = usuarioService.buscarDadosUsuario(id);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Usuário não encontrado!");
        }
        if (numero != null) {
            if (numero.equals(usuario.getTelefone()) && usuario.getId() != id) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: telefone já cadastrado no sistema.");
            }
        }
        if (email != null) {
            if (email.equals(usuario.getEmail()) && usuario.getId() != id) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: e-mail já cadastrado no sistema.");
            }
        }
//        try {
//            if (foto != null) {
//                usuario.setFoto(foto.getBytes());
//            }
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar imagem");
//        }

        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setTelefone(numero);
        usuarioService.atualizarDados(usuario, id);
        return ResponseEntity.ok(Map.of("mensagem", "Dados atualizados com sucesso!"));
    }

}
