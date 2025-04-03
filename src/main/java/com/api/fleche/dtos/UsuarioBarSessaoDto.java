package com.api.fleche.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UsuarioBarSessaoDto {

    @NotNull
    private Long usuarioId;

    @NotNull
    private Long barId;

}

