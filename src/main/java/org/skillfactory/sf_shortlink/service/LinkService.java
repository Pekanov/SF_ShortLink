package org.skillfactory.sf_shortlink.service;

import lombok.RequiredArgsConstructor;
import org.skillfactory.sf_shortlink.model.entity.ShortenedLink;
import org.skillfactory.sf_shortlink.repository.ShortenedLinkRepository;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final ShortenedLinkRepository shortenedLinkRepository;


    public String createShortenedLink(String originalUrl, UUID userId, Integer maxClicks, Duration ttl) {
        String shortUrl = generateShortUrl();
        LocalDateTime expirationDate = LocalDateTime.now().plus(ttl);

        ShortenedLink link = ShortenedLink.builder().
                shortUrl(shortUrl).
                expirationDate(expirationDate).
                userId(userId).
                originalUrl(originalUrl).
                maxClicks(maxClicks).
                build();

        return shortenedLinkRepository.save(link).getShortUrl();
    }

    public String redirect(String shortUrl) throws Exception {
        ShortenedLink link = shortenedLinkRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new Exception("Link not found"));

        if (link.getMaxClicks() <= 0) {
            throw new Exception("Max clicks exceeded");
        }

        if (LocalDateTime.now().isAfter(link.getExpirationDate())) {
            throw new Exception("Link expired");
        }

        link.setMaxClicks(link.getMaxClicks() - 1);

        return shortenedLinkRepository.save(link).getOriginalUrl();
    }

    public void cleanupExpiredLinks() {
        List<ShortenedLink> expiredLinks = shortenedLinkRepository.findByExpirationDateBefore(LocalDateTime.now());
        shortenedLinkRepository.deleteAll(expiredLinks);
    }

    private String generateShortUrl() {
        return "clck.ru/" + UUID.randomUUID().toString().substring(0, 6);
    }



}
