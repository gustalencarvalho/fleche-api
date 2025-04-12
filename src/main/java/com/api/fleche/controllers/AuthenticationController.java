package com.api.fleche.controllers;

import com.api.fleche.dtos.UsuarioDto;
import com.api.fleche.models.Usuario;
import com.api.fleche.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:8100")
public class AuthenticationController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/singup")
    public ResponseEntity<Object> criarConta(@RequestBody @Valid UsuarioDto usuarioDto) {
        if (usuarioService.existsByEmail(usuarioDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email já cadastrado!");
        }
        if (usuarioService.existsByNumero(usuarioDto.getNumero())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Telefone já cadastrado!");
        }
        if (!usuarioService.verificaIdade(usuarioDto.getDataNascimento())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Precisa ter 18 anos ou mais");
        }

        var usuarioModel = new Usuario();
        BeanUtils.copyProperties(usuarioDto, usuarioModel);
        usuarioModel.setDataDeCriacao(LocalDateTime.now(ZoneId.of("UTC")));
        usuarioService.criarConta(usuarioModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioModel);
    }

}
