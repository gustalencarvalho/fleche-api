package com.api.fleche.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "TB_CMD")
@Data
public class ComandosSql {

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
