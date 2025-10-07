package com.api.fleche.controller;

import com.api.fleche.model.dtos.LocationDto;
import com.api.fleche.model.dtos.StandardError;
import com.api.fleche.model.dtos.UserBarDto;
import com.api.fleche.model.dtos.UserLocationSessionDto;
import com.api.fleche.service.LocationService;
import com.api.fleche.service.UserLocationSessionService;
import com.api.fleche.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/session")
@Tag(name = "UserLocationSessionController", description = "Controller responsible for checkin in user")
@RequiredArgsConstructor
public class UserLocationSessionController {

    private final UserLocationSessionService userLocationSessionService;
    private final LocationService locationService;
    private final UserService userService;

    @PostMapping("/checkin")
    @Operation(summary = "Checkin user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Checkin"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)))
    })
    public ResponseEntity<Object> checkinUser(@RequestBody @Valid UserLocationSessionDto userLocationSessionDto) {
        userLocationSessionService.checkin(userLocationSessionDto);
        return ResponseEntity.status(HttpStatus.OK).body("Check-in realized!");
    }

    @PatchMapping("/checkout/{userId}")
    public ResponseEntity<Object> checkoutUser(@PathVariable Long userId) {
        userLocationSessionService.checkout(userId);
        return ResponseEntity.status(HttpStatus.OK).body("Check-out realized");
    }

    @GetMapping("/users/online/{userId}")
    public ResponseEntity<Page<UserBarDto>> usersList(@PathVariable Long userId, @RequestParam(value = "page") int page, @RequestParam(value = "size") int size) {
        var user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        var bar = userLocationSessionService.findByLocationId(user.get().getId());
        if (bar == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        String qrCode = userLocationSessionService.qrCodeBar(bar);
        Pageable pageable = PageRequest.of(page, size);
        Page<UserBarDto> usuarioBarDtos = userLocationSessionService.usuariosParaListar(qrCode, userId, pageable);
        return ResponseEntity.ok(usuarioBarDtos);
    }

    @GetMapping("/usuarios/{userId}/online")
    public ResponseEntity<List<LocationDto>> userssOnline(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userLocationSessionService.listTotalUserBar(userId));
    }

    @GetMapping("/usuario/{userId}/autenticado")
    public ResponseEntity<Object> verifyIfUserOnline(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("message", userLocationSessionService.verificaSeUsuarioEstaOnline(userId)));
    }

}
