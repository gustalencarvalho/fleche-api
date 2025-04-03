package com.api.fleche.services;

import com.api.fleche.models.Bar;

public interface BarService {
    Bar registrarBar(Bar bar);
    boolean existsByCnpj(String cnpj);
}
