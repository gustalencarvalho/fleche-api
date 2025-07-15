package com.api.fleche.controllers;

import com.api.fleche.dtos.AuthenticationDto;
import com.api.fleche.dtos.LoginResponseDto;
import com.api.fleche.dtos.UsuarioDto;
import com.api.fleche.infra.security.TokenService;
import com.api.fleche.models.Usuario;
import com.api.fleche.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDto authenticationDto) {
        try {
            var userNamePassword = new UsernamePasswordAuthenticationToken(authenticationDto.telefone(), authenticationDto.senha());
            var auth = authenticationManager.authenticate(userNamePassword);
            var token = tokenService.generateToken((Usuario) auth.getPrincipal());
            return ResponseEntity.ok(new LoginResponseDto(token));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas: " + ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
        }

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
