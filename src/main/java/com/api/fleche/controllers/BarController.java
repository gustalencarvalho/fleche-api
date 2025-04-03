package com.api.fleche.controllers;

import com.api.fleche.dtos.BarRegistroDto;
import com.api.fleche.dtos.BaresDto;
import com.api.fleche.models.Bar;
import com.api.fleche.services.BarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bares")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class BarController {

    private final BarService barService;

    @PostMapping("/singup")
    public ResponseEntity<Object> registrarBar(@RequestBody @Valid BarRegistroDto barRegistroDto) {
        if (barService.existsByCnpj(barRegistroDto.getCnpj())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: CNPJ já registrado na base de dados!");
        }

        var barModel = new Bar();
        BeanUtils.copyProperties(barRegistroDto, barModel);
        barModel.setDataRegistro(LocalDateTime.now(ZoneId.of("UTC")));
        barService.registrarBar(barModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(barModel);
    }

    @GetMapping
    public ResponseEntity<List<BaresDto>> buscaTodosBares() {
        return ResponseEntity.status(HttpStatus.OK).body(barService.findAll());
    }

}
