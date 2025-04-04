package com.api.fleche.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "TB_CMD")
@Data
public class ComandosSql implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cmdId;

    @Column(name = "CMD_SQL", columnDefinition = "TEXT")
    private String cmdSql;

    @Column(name = "CMD_DESCRICAO")
    private String cmdDescricao;

    @Column(name = "CMD_STATUS")
    private String cmdStatus;

}
