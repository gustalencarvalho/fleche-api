package com.api.fleche.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDadosDto {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String status;
    private String statusUsuarioBar;
    private String nomeBar;

}
