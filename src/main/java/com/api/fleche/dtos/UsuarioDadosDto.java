package com.api.fleche.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDadosDto {

    private Long id;
    private String nome;
    private String genero;
    private String email;
    private String numero;
    @Column
    @Lob
    private byte[] foto;
    private String status;
    private String preferencia;
    private String statusUsuarioBar;
    private String nomeBar;

}
