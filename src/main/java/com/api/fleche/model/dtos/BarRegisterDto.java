package com.api.fleche.model.dtos;

import com.api.fleche.enums.States;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BarRegisterDto {

    @NotBlank(message = "Bar name is required")
    @Size(max = 150, message = "The bar name must have a maximum of 150 characters")
    private String name;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Bar number required")
    private Integer number;

    @NotBlank(message = "District is required")
    private String district;

    @NotBlank(message = "CNPJ is required")
    private String cnpj;

    @NotBlank(message = "City is required")
    private String city;

    @NotNull(message = "State is required")
    private States states;

    @Size(max = 20, message = "The phone number must have a maximum of 20 characters")
    private String phone;

}
