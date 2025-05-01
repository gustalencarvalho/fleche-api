package com.api.fleche.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "TB_PERFIL_USUARIO")
@Data
public class PerfilUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Lob
    private byte[] foto;

    @Column
    private String genero;

    @Column
    private String preferencia;

    @Column
    @Size(max = 150)
    private String bio;

    @Column
    private String filme;

    @Column
    private String lazer;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuarioId;

}
