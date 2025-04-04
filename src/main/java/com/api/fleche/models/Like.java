package com.api.fleche.models;

import com.api.fleche.enums.StatusLike;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "TB_LIKES", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"usuario_id_origem", "usuario_id_destino"})
})
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIKE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID_ORIGEM", nullable = false)
    private Usuario usuarioOrigem;

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID_DESTINO", nullable = false)
    private Usuario usuarioDestino;

    @Column(name = "STATUS_LIKE", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusLike status;

    @Column(name = "DATA_LIKE", nullable = false, updatable = false)
    private LocalDateTime dataLike = LocalDateTime.now();

    public Like(Usuario usuarioOrigem, Usuario usuarioDestino, StatusLike status) {
        this.usuarioOrigem = usuarioOrigem;
        this.usuarioDestino = usuarioDestino;
        this.status = status;
        this.dataLike = LocalDateTime.now();
    }

}