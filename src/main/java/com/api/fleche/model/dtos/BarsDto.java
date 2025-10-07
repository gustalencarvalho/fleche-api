package com.api.fleche.model.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BarsDto {

    private Long id;
    private String name;
    private String address;
    private String district;
    private String city;
    private Integer phone;
    private String qrCode;
    private Long usersOnline;

}
