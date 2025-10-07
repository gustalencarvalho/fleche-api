package com.api.fleche.model;

import com.api.fleche.enums.States;
import com.api.fleche.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TB_BARS")
public class Bar implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 20, unique = true)
    private String cnpj;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ACTIVE;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private States states;

    @Column(length = 20)
    private String phone;

    @Column(nullable = false, unique = true)
    private String qrCode;

    @Column(name = "date_registry", updatable = false)
    @CreationTimestamp
    private LocalDateTime dateRegistry;

    @PrePersist
    public void prePersist() {
        this.dateRegistry = LocalDateTime.now(ZoneId.of("UTC"));
        this.qrCode = UUID.randomUUID().toString();
    }

}