package com.api.fleche.service;

import com.api.fleche.model.ProfileUser;
import com.api.fleche.model.User;
import com.api.fleche.repository.ProfilerUserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileUserService {

    private final ProfilerUserRespository profilerUserRespository;

    public void saveProfile(ProfileUser profileUser) {
        profilerUserRespository.save(profileUser);
    }

    public Optional<ProfileUser> findByUserId(Optional<User> user) {
        return profilerUserRespository.findByUserId(user);
    }

}
