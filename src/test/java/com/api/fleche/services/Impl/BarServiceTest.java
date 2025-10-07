package com.api.fleche.services.Impl;

import com.api.fleche.model.dtos.BarRegisterDto;
import com.api.fleche.model.Bar;
import com.api.fleche.repository.BarRepository;
import com.api.fleche.service.BarService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class BarServiceTest {

    @InjectMocks
    private BarService barService;

    @Mock
    private BarRepository barRepository;

    @Test
    @DisplayName("Cria cadastro do bar")
    BarRegisterDto criaCadastroDeUmBarBar(BarRegisterDto bar) {
        Bar registro = barService.registerBar(bar);
        return bar;
    }
}