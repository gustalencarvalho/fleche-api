package com.api.fleche.controllers;

import com.api.fleche.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/dados/{numero}")
    public ResponseEntity<Object> buscarDadosDoUsuario(@PathVariable String numero) {
        var usuario = usuarioService.buscarDadosUsuario(numero);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Usuário não encontrado!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

}
