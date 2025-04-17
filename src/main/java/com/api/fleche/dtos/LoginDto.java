package com.api.fleche.dtos;

import lombok.Data;

@Data
public class LoginDto {
    private Long id;
    private String nome;
    private String email;
    private String numero;
    private String senha;

    public LoginDto(Long id, String nome, String email, String numero, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.numero = numero;
        this.senha = senha;
    }
}
