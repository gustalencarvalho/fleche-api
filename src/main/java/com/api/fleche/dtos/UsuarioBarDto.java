package com.api.fleche.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioBarDto {

    private Long id;
    private String nome;
    private int idade;

}
