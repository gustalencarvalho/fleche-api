package com.api.fleche.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioBarDto {

    private String nome;
    private String genero;
    private int idade;

}
