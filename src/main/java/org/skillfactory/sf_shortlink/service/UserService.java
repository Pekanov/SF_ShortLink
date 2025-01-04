package org.skillfactory.sf_shortlink.service;

import lombok.RequiredArgsConstructor;
import org.skillfactory.sf_shortlink.model.entity.User;
import org.skillfactory.sf_shortlink.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUser(UUID userId) {
        final UUID resolvedUserId = (userId != null) ? userId : UUID.randomUUID(); // Финальная переменная

        return userRepository.findById(resolvedUserId).orElseGet(() -> {
            User newUser = User.builder().id(resolvedUserId).build();
            return userRepository.save(newUser);
        });
    }

    public UUID checkUser(UUID userId) {
        if (userId == null) {
            return null;
        }
        return userRepository.findById(userId)
                .map(User::getId)
                .orElse(null);
    }


}
