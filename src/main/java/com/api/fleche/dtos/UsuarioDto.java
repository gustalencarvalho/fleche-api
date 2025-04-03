package com.api.fleche.dtos;

import com.api.fleche.enums.Genero;
import com.api.fleche.enums.Preferencia;
import com.api.fleche.enums.Status;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UsuarioDto {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "O número é obrigatório")
    @Size(min = 10, max = 20, message = "O número deve ter entre 10 e 20 caracteres")
    private String numero;

    @NotNull(message = "A data de nascimento é obrigatória")
    private LocalDate dataNascimento;

    @NotNull(message = "O gênero é obrigatório")
    private Genero genero;

    @NotNull(message = "O status é obrigatório")
    private Status status;

    @NotNull(message = "A preferência é obrigatória")
    private Preferencia preferencia;

    public UsuarioDto() {
        this.status = Status.ATIVO;
    }

}
