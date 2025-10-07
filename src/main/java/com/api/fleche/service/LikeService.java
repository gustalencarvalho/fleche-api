package com.api.fleche.service;

import com.api.fleche.enums.StatusLike;
import com.api.fleche.model.Like;
import com.api.fleche.model.User;
import com.api.fleche.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;

    public Like darLike(User userOrigem, User userDestiny, StatusLike statusLike) {
        Optional<Like> likeExist = likeRepository.findByUserOrigemAndUserDestiny(userOrigem, userDestiny);
        if (likeExist.isPresent()) {
            Like like = likeExist.get();
            like.setStatus(statusLike);
            return likeRepository.save(like);
        }
        Like newLike = new Like(userOrigem, userDestiny, statusLike);
        return likeRepository.save(newLike);
    }

    public boolean verifyMatch(User userOrigem, User userDestiny) {
        return likeRepository.existsByUserOrigemAndUserDestinyAndStatus(
                userOrigem,
                userDestiny,
                StatusLike.LIKE)
                &&
                likeRepository.existsByUserOrigemAndUserDestinyAndStatus(
                        userDestiny,
                        userOrigem,
                        StatusLike.LIKE);
    }

}
