package org.skillfactory.sf_shortlink.service;

import org.skillfactory.sf_shortlink.model.entity.ShortenedLink;
import org.skillfactory.sf_shortlink.model.entity.User;
import org.skillfactory.sf_shortlink.model.entity.UserNotification;
import org.skillfactory.sf_shortlink.repository.UserNotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserNotificationService {

    private final UserNotificationRepository userNotificationRepository;
    private final UserService userService;

    public UserNotificationService(UserNotificationRepository userNotificationRepository, UserService userService) {
        this.userNotificationRepository = userNotificationRepository;
        this.userService = userService;
    }

    public void addNotification(ShortenedLink shortenedLink, UUID userId, String message) {
        try {
            User user = userService.getUser(userId);
            UserNotification userNotification = UserNotification.builder()
                    .user(user)
                    .shortenedLink(shortenedLink)
                    .message(message)
                    .build();
            userNotificationRepository.save(userNotification);

        }catch (Exception e) {
            throw new RuntimeException("Error while adding notification", e);
        }
    }

    public List<UserNotification> getUserNotifications(UUID userId) {
        try {
            return userNotificationRepository.findAllByUserId(userId);
        }catch (Exception e) {
            throw new RuntimeException("Error while getting user notifications", e);
        }
    }

}
