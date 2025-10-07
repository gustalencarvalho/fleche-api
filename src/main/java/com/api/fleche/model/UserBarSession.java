package com.api.fleche.model;

import com.api.fleche.enums.StatusUserBar;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "USER_BAR_SESSION", uniqueConstraints = {
        @UniqueConstraint(columnNames = "user_id")
})
public class UserBarSession implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "bar_id", nullable = false)
    private Bar bar;

    @Column(nullable = false)
    private LocalDateTime dateActive;

    @Column(nullable = false)
    private LocalDateTime dateExpires;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusUserBar statusUserBar;

}


