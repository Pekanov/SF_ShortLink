package org.skillfactory.sf_shortlink.utils;

import lombok.RequiredArgsConstructor;
import org.skillfactory.sf_shortlink.service.LinkService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LinkCleanupTask {

    private final LinkService linkService;


    @Scheduled(cron = "0 0 * * * *") // Run every hour
    public void cleanupExpiredLinks() {
        linkService.cleanupExpiredLinks();
    }
}
