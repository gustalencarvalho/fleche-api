package com.api.fleche.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerfilUsuarioDto {

    private Long id;

    @Column
    @Lob
    private byte[] foto;

    @Column(nullable = false)
    private String genero;

    @Column(nullable = false, length = 150)
    private String bio;

    @Column(nullable = false)
    private String filme;

    @Column(nullable = false)
    private String lazer;

    @Column(nullable = false)
    private String preferenciaGenero;

}
