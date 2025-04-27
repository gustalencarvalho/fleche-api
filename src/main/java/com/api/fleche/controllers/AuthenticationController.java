package com.api.fleche.controllers;

import com.api.fleche.dtos.AuthenticationDto;
import com.api.fleche.dtos.LoginDto;
import com.api.fleche.dtos.LoginResponseDto;
import com.api.fleche.dtos.UsuarioDto;
import com.api.fleche.infra.security.TokenService;
import com.api.fleche.models.Usuario;
import com.api.fleche.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @GetMapping("/api")
    public ResponseEntity<String> api() {
        return ResponseEntity.status(HttpStatus.OK).body("E ai calabreso");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDto authenticationDto) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(authenticationDto.telefone(), authenticationDto.senha());
        var auth = authenticationManager.authenticate(userNamePassword);
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid UsuarioDto usuarioDto) {
        if (usuarioService.existsByEmail(usuarioDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email já cadastrado!");
        }
        LocalDate nascimento = LocalDate.parse(usuarioDto.getDataNascimento().toString());
        if (!usuarioService.verificaIdade(nascimento)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Precisa ter 18 anos ou mais");
        }
        if (usuarioService.findByTelefone(usuarioDto.getTelefone()) != null) {
            return ResponseEntity.badRequest().build();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(usuarioDto.getSenha());
        Usuario novoUsuario = new Usuario(usuarioDto.getNome(), usuarioDto.getEmail(), usuarioDto.getDdd(), usuarioDto.getTelefone(), usuarioDto.getDataNascimento(), encryptedPassword, usuarioDto.getRole());
        usuarioService.criarConta(novoUsuario);
        return ResponseEntity.ok().build();
    }

}
