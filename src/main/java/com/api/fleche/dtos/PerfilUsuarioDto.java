package com.api.fleche.dtos;

import com.api.fleche.enums.Genero;
import com.api.fleche.enums.Preferencia;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerfilUsuarioDto {

    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Genero genero;

    @Column(nullable = false, length = 150)
    private String bio;

    @Lob
    private byte[] foto;

    private String filme;

    private String lazer;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Preferencia preferencia;


}
