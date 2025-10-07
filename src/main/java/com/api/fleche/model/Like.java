package com.api.fleche.model;

import com.api.fleche.enums.StatusLike;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "TB_LIKES", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id_origem", "user_id_destiny"})
})
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIKE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID_ORIGEM", nullable = false)
    private User userOrigem;

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID_DESTINY", nullable = false)
    private User userDestiny;

    @Column(name = "STATUS_LIKE", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusLike status;

    @Column(name = "DATA_LIKE", nullable = false, updatable = false)
    private LocalDateTime dateLike = LocalDateTime.now();

    public Like(User userOrigem, User userDestiny, StatusLike status) {
        this.userOrigem = userOrigem;
        this.userDestiny = userDestiny;
        this.status = status;
        this.dateLike = LocalDateTime.now();
    }

}