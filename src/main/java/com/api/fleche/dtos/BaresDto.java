package com.api.fleche.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaresDto {

    private Long id;
    private String nome;
    private String endereco;
    private String bairro;
    private String cidade;
    private Integer numero;
    private String qrCode;
    private Long usuariosOnline;

}
