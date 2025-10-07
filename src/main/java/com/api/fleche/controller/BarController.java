package com.api.fleche.controller;

import com.api.fleche.model.dtos.BarRegisterDto;
import com.api.fleche.model.dtos.BarsDto;
import com.api.fleche.model.dtos.StandardError;
import com.api.fleche.service.BarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bars")
public class BarController {

    private final BarService barService;

    @PostMapping("/singup")
    @Operation(summary = "Save new Bar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bar register"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)))
    })
    public ResponseEntity<Object> registerBar(@RequestBody @Valid BarRegisterDto barRegisterDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(barService.registerBar(barRegisterDto));
    }

    @GetMapping
    public ResponseEntity<List<BarsDto>> findAllBars() {
        return ResponseEntity.status(HttpStatus.OK).body(barService.findAll());
    }

}
