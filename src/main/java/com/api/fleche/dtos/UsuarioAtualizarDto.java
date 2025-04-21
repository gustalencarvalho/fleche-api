package com.api.fleche.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class UsuarioAtualizarDto {
    private String nome;
    private String email;
    private String numero;
    private String genero;
    private String preferencia;
    @Column
    @Lob
    private byte[] foto;
}
