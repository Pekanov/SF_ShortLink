package org.skillfactory.sf_shortlink.model.entity;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "link")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShortenedLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalUrl;
    private String shortUrl;
    private UUID userId;

    private Integer maxClicks;

    private LocalDateTime expirationDate;


}
