package com.api.fleche.services;

import com.api.fleche.dtos.BaresDto;
import com.api.fleche.models.Bar;

import java.util.List;

public interface BarService {
    Bar registrarBar(Bar bar);
    boolean existsByCnpj(String cnpj);
    Bar findbyId(Long barId);
    List<BaresDto> findAll();
}
