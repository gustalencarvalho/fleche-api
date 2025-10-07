package com.api.fleche.service;

import com.api.fleche.model.Bar;
import com.api.fleche.model.dtos.BarRegisterDto;
import com.api.fleche.model.dtos.BarsDto;
import com.api.fleche.model.exception.CnpjAlreadyExistsException;
import com.api.fleche.repository.BarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BarService {

    private final BarRepository barRepository;

    public Bar registerBar(BarRegisterDto barRegisterDto) {
        if (existsByCnpj(barRegisterDto.getCnpj())) {
            throw new CnpjAlreadyExistsException("CNPJ already exists");
        }
        var barModel = new Bar();
        BeanUtils.copyProperties(barRegisterDto, barModel);
        return barRepository.save(barModel);
    }

    public boolean existsByCnpj(String cnpj) {
        return barRepository.existsByCnpj(cnpj);
    }

    public Bar findbyQrCode(String qrCode) {
        Optional<Bar> bar = barRepository.findByQrCode(qrCode);
        return bar.get();
    }

    public List<BarsDto> findAll() {
        return barRepository.findAll()
                .stream()
                .map(bar -> new BarsDto(
                        bar.getId(),
                        bar.getName(),
                        bar.getAddress(),
                        bar.getDistrict(),
                        bar.getCity(),
                        bar.getNumber(),
                        bar.getQrCode(),
                        null))
                .collect(Collectors.toList());
    }

}
