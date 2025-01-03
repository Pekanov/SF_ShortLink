package org.skillfactory.sf_shortlink.repository;

import org.skillfactory.sf_shortlink.model.entity.ShortenedLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShortenedLinkRepository extends JpaRepository<ShortenedLink, Long> {
    Optional<ShortenedLink> findByShortUrl(String shortUrl);
    List<ShortenedLink> findByExpirationDateBefore(LocalDateTime dateTime);
}
