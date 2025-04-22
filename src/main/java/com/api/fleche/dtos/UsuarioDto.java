package com.api.fleche.dtos;

import com.api.fleche.enums.Status;
import com.api.fleche.enums.UserRole;
import com.api.fleche.models.Perfil;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDto {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Email(message = "E-mail inválido")
    private String email;

    @Size(min = 10, max = 20, message = "O número deve ter entre 10 e 20 caracteres")
    private String telefone;

    @NotNull
    @Size(max = 2, message = "O DDD deve conter apenas dois digitos")
    private String ddd;

    @NotNull(message = "A data de nascimento é obrigatória")
    private LocalDate dataNascimento;

    @Column(nullable = false, length = 150)
    private String senha;

    private UserRole role;

    private Status status;

    public UsuarioDto() {
        this.status = Status.ATIVO;
    }

}
