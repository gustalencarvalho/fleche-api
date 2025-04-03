package com.api.fleche.dtos;

import com.api.fleche.enums.Estados;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.UUID;

@Data
public class BarRegistroDto {

    @NotBlank(message = "O nome do bar é obrigatório")
    @Size(max = 150, message = "O nome do bar deve ter no máximo 150 caracteres")
    private String nome;

    @NotBlank(message = "O endereço é obrigatório")
    private String endereco;

    @NotNull(message = "O número do bar é obrigatório")
    private Integer numero;

    @NotBlank(message = "O bairro é obrigatório")
    private String bairro;

    @NotBlank(message = "O cnpj é obrigatório")
    private String cnpj;

    @NotBlank(message = "A cidade é obrigatória")
    private String cidade;

    @NotNull(message = "O estado é obrigatório")
    private Estados estado;

    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
    private String telefone;

    @NotNull(message = "O QR Code é obrigatório")
    private String qrCode;

}
