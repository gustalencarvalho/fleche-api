package com.api.fleche.models;

import jakarta.persistence.*;

@Entity
@Table(name = "TB_PERFIL_USUARIO")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Lob
    private byte[] foto;

    @Column
    private String genero;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuarioId;

}
