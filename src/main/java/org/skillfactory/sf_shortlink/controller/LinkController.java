package org.skillfactory.sf_shortlink.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.skillfactory.sf_shortlink.model.dto.CreateShortLinkDTO;
import org.skillfactory.sf_shortlink.model.dto.RedirectLinkDTO;
import org.skillfactory.sf_shortlink.service.LinkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.UUID;

@RestController
@RequestMapping("/api/link")
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping("/shortingLink")
    public ResponseEntity<?> shortenLink(@RequestBody CreateShortLinkDTO createShortLinkDTO) {
        UUID userId = UUID.randomUUID(); // Replace with a persistent UUID for a real implementation
        return ResponseEntity.ok(
                linkService.createShortenedLink(
                        createShortLinkDTO.getOriginalUrl(),
                        userId,
                        createShortLinkDTO.getMaxClicks(),
                        Duration.ofDays(createShortLinkDTO.getDurationDay())
                )
        );
    }


    @GetMapping()
    public void redirect(@RequestBody RedirectLinkDTO redirectLinkDTO, HttpServletResponse response) throws Exception {
        String originalUrl = linkService.redirect(redirectLinkDTO.getShortLink());
        response.sendRedirect(originalUrl);
    }

}
