package com.api.fleche.models;

import com.api.fleche.enums.StatusUsuarioBar;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "USUARIO_BAR_SESSAO", uniqueConstraints = {
        @UniqueConstraint(columnNames = "usuario_id")
})
public class UsuarioBarSessao implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "bar_id", nullable = false)
    private Bar bar;

    @Column(nullable = false)
    private LocalDateTime dataAtivacao;

    @Column(nullable = false)
    private LocalDateTime dataExpiracao;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusUsuarioBar statusUsuarioBar;

}


