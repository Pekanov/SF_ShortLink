package org.skillfactory.sf_shortlink.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.skillfactory.sf_shortlink.model.dto.CreateShortLinkDTO;
import org.skillfactory.sf_shortlink.model.dto.DeleteLinkDTO;
import org.skillfactory.sf_shortlink.model.dto.RedirectLinkDTO;
import org.skillfactory.sf_shortlink.model.dto.UpdateShortLinkDTO;
import org.skillfactory.sf_shortlink.model.entity.User;
import org.skillfactory.sf_shortlink.service.LinkService;
import org.skillfactory.sf_shortlink.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.UUID;

@RestController
@RequestMapping("/api/link")
public class LinkController {

    private final LinkService linkService;
    private final UserService userService;

    public LinkController(LinkService linkService, UserService userService) {
        this.linkService = linkService;
        this.userService = userService;
    }

    @PostMapping("/shortingLink")
    public ResponseEntity<?> shortenLink(@RequestBody CreateShortLinkDTO createShortLinkDTO) {

        User user = userService.getUser(createShortLinkDTO.getUserId());
        return ResponseEntity.ok(
                linkService.createShortenedLink(
                        createShortLinkDTO.getOriginalUrl(),
                        user,
                        createShortLinkDTO.getMaxClicks(),
                        Duration.ofDays(createShortLinkDTO.getDurationDay())
                ).toString()
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserLinks(@PathVariable UUID userId) {
        return ResponseEntity.ok(linkService.getLinksByUserId(userId));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateLink(@RequestBody UpdateShortLinkDTO updateShortLinkDTO) {
        if (userService.checkUser(updateShortLinkDTO.getUserId()) != null){
            linkService.updateLink(updateShortLinkDTO);
            return ResponseEntity.ok("Link updated successfully");
        }else return ResponseEntity.badRequest().body("User doesn't exist or permission denied");

    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteLink(@RequestBody DeleteLinkDTO deleteLinkDTO) {
        if (userService.checkUser(deleteLinkDTO.getUserId()) != null){
            linkService.deleteLink(deleteLinkDTO);
            return ResponseEntity.ok("Link deleted successfully");
        }else return ResponseEntity.badRequest().body("User doesn't exist or permission denied");
    }


    @GetMapping()
    public void redirect(@RequestBody RedirectLinkDTO redirectLinkDTO, HttpServletResponse response) throws Exception {
        String originalUrl = linkService.redirect(redirectLinkDTO.getShortLink());
        response.sendRedirect(originalUrl);
    }

}
