package com.api.fleche.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaresDto {

    private Long id;
    private String nome;
    private String endereco;
    private String bairro;
    private String cidade;
    private Integer numero;
    private String qrCode;

}
