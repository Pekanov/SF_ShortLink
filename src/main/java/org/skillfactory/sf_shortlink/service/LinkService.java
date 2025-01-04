package org.skillfactory.sf_shortlink.service;

import lombok.RequiredArgsConstructor;
import org.skillfactory.sf_shortlink.model.dto.*;
import org.skillfactory.sf_shortlink.model.entity.ShortenedLink;
import org.skillfactory.sf_shortlink.model.entity.User;
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

    public ShortenedLink createShortenedLink(String originalUrl, User user, Integer maxClicks, Duration ttl) {
        String shortUrl = generateShortUrl();
        LocalDateTime expirationDate = LocalDateTime.now().plus(ttl);

        ShortenedLink link = ShortenedLink.builder()
                .shortUrl(shortUrl)
                .expirationDate(expirationDate)
                .originalUrl(originalUrl)
                .maxClicks(maxClicks)
                .clicksCount(0)
                .user(user)
                .build();

        return shortenedLinkRepository.save(link);
    }

    public List<ShortenedLink> getLinksByUserId(UUID userId) {
        return shortenedLinkRepository.findAllByUserId(userId);
    }

    public void updateLink(UpdateShortLinkDTO dto) {
        ShortenedLink link = shortenedLinkRepository.findByShortUrl(dto.getShortLink())
                .orElseThrow(() -> new RuntimeException("Link not found"));

        link.setMaxClicks(dto.getMaxClicks());
        link.setExpirationDate(LocalDateTime.now().plusDays(dto.getDurationDay()));
        shortenedLinkRepository.save(link);
    }

    public void deleteLink(DeleteLinkDTO dto) {
        if (dto.getShortUrl() != null) {
            shortenedLinkRepository.findByShortUrl(dto.getShortUrl())
                    .ifPresent(shortenedLinkRepository::delete);
        } else if (dto.getOriginalUrl() != null) {
            shortenedLinkRepository.findByOriginalUrl(dto.getOriginalUrl())
                    .ifPresent(shortenedLinkRepository::delete);
        } else {
            throw new RuntimeException("Both shortUrl and originalUrl are null");
        }
    }

    private String generateShortUrl() {
        return "clck.ru/" + UUID.randomUUID().toString().substring(0, 6);
    }

    public String redirect(String shortUrl) throws Exception {
        ShortenedLink link = shortenedLinkRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new Exception("Link not found"));

        if (link.getClicksCount() >= link.getMaxClicks()) {
            throw new Exception("Max clicks exceeded");
        }

        if (LocalDateTime.now().isAfter(link.getExpirationDate())) {
            throw new Exception("Link expired");
        }

        link.setClicksCount(link.getClicksCount() + 1);

        return shortenedLinkRepository.save(link).getOriginalUrl();
    }

    public void cleanupExpiredLinks() {
        List<ShortenedLink> expiredLinks = shortenedLinkRepository.findByExpirationDateBefore(LocalDateTime.now());
        shortenedLinkRepository.deleteAll(expiredLinks);
    }
}
