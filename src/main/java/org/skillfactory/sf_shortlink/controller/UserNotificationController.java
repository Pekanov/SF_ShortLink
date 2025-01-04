package org.skillfactory.sf_shortlink.controller;


import lombok.RequiredArgsConstructor;
import org.skillfactory.sf_shortlink.service.UserNotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class UserNotificationController {

    private final UserNotificationService userNotificationService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserNotification(@PathVariable("userId") UUID userId) {
        return ResponseEntity.ok(userNotificationService.getUserNotifications(userId));
    }

}
