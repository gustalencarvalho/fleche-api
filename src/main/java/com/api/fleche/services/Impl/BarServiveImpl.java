package com.api.fleche.services.Impl;

import com.api.fleche.models.Bar;
import com.api.fleche.repositories.BarRepository;
import com.api.fleche.services.BarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BarServiveImpl implements BarService {

    @Autowired
    private BarRepository barRepository;

    @Override
    public Bar registrarBar(Bar bar) {
        return barRepository.save(bar);
    }

    @Override
    public boolean existsByCnpj(String cnpj) {
        return barRepository.existsByCnpj(cnpj);
    }

}
