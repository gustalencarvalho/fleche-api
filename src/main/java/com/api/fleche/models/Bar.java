package com.api.fleche.models;

import com.api.fleche.enums.Estados;
import com.api.fleche.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "TB_BARES")
public class Bar implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 20, unique = true)
    private String cnpj;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private Integer numero;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String cidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ATIVO;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Estados estado;

    @Column(length = 20)
    private String telefone;

    @Column(nullable = false, unique = true)
    private String qrCode;

    @Column(name = "data_registro", updatable = false)
    @CreationTimestamp
    private LocalDateTime dataRegistro;

}