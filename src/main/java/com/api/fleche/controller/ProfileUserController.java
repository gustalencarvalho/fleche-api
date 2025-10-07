package com.api.fleche.controller;

import com.api.fleche.model.dtos.ProfileUserDto;
import com.api.fleche.model.ProfileUser;
import com.api.fleche.service.ProfileUserService;
import com.api.fleche.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileUserController {

    private final ProfileUserService profileUserService;
    private final UserService userService;

    @GetMapping("/{id}/picture")
    public ResponseEntity<byte[]> getFoto(@PathVariable Long id) {
        var user = userService.findById(id);
        Optional<ProfileUser> profileUser = profileUserService.findByUserId(user);
        if (profileUser.isPresent() && profileUser.get().getPicture() != null) {
            byte[] imagem = profileUser.get().getPicture();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imagem, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestPart("profile") @Valid ProfileUserDto profileUserDto,
            @RequestPart(value = "picture", required = false) MultipartFile pic) {
        try {
            var user = userService.findById(profileUserDto.getId());
            var profileUser = new ProfileUser();
            profileUser.setGender(profileUserDto.getGender().name());
            profileUser.setBio(profileUserDto.getBio());
            profileUser.setPreferences(profileUserDto.getPreferences().name());
            if (pic != null && !pic.isEmpty()) {
                profileUser.setPicture(pic.getBytes());
            }
            profileUser.setUserId(user.get());
            profileUserService.saveProfile(profileUser);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
