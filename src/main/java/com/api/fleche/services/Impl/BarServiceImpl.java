package com.api.fleche.services.Impl;

import com.api.fleche.dtos.BaresDto;
import com.api.fleche.models.Bar;
import com.api.fleche.repositories.BarRepository;
import com.api.fleche.services.BarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BarServiceImpl implements BarService {

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

    @Override
    public Bar findbyQrCode(String qrCode) {
        Optional<Bar> bar = barRepository.findByQrCode(qrCode);
        return bar.get();
    }

    @Override
    public List<BaresDto> findAll() {
        return barRepository.findAll()
                .stream()
                .map(bar -> new BaresDto(bar.getId(), bar.getNome(), bar.getEndereco(), bar.getBairro(), bar.getCidade(), bar.getNumero()))
                .collect(Collectors.toList());
    }

}
