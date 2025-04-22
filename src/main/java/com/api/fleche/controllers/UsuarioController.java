package com.api.fleche.controllers;

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
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

//    @PostMapping(value = "/singup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Object> criarConta(
//            @RequestParam("nome") String nome,
//            @RequestParam("email") String email,
//            @RequestParam("numero") String numero,
//            @RequestParam("senha") String senha,
//            @RequestParam("dataNascimento") String dataNascimento,
//            @RequestParam("foto") MultipartFile foto) {
//
//        if (usuarioService.existsByEmail(email)) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email já cadastrado!");
//        }
//        if (usuarioService.existsByTelefone(numero)) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Telefone já cadastrado!");
//        }
//        LocalDate nascimento = LocalDate.parse(dataNascimento);
//        if (!usuarioService.verificaIdade(nascimento)) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Precisa ter 18 anos ou mais");
//        }
//
//        Usuario usuarioModel = new Usuario();
//        usuarioModel.setNome(nome);
//        usuarioModel.setEmail(email);
//        usuarioModel.setTelefone(numero);
//        usuarioModel.setSenha(senha);
//        usuarioModel.setDataNascimento(nascimento);
////        try {
////            usuarioModel.setFoto(foto.getBytes());
////        } catch (IOException e) {
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar imagem");
////        }
//        usuarioModel.setDataDeCriacao(LocalDateTime.now(ZoneId.of("UTC")));
//        usuarioService.criarConta(usuarioModel);
//        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioModel);
//    }

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
//        if (usuario.isPresent() && usuario.get().getFoto() != null) {
//            byte[] imagem = usuario.get().getFoto();
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.IMAGE_JPEG);
//            return new ResponseEntity<>(imagem, headers, HttpStatus.OK);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
        return null;
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
            if (numero.equals(usuario.getNumero()) && usuario.getId() != id) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: telefone já cadastrado no sistema.");
            }
        }
        if (email != null) {
            if (email.equals(usuario.getEmail()) && usuario.getId() != id) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: e-mail já cadastrado no sistema.");
            }
        }
        try {
            if (foto != null) {
                usuario.setFoto(foto.getBytes());
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar imagem");
        }

        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setNumero(numero);
        usuario.setGenero(genero);
        usuario.setPreferencia(preferencia);
        usuarioService.atualizarDados(usuario, id);
        return ResponseEntity.ok(Map.of("mensagem", "Dados atualizados com sucesso!"));
    }

}
