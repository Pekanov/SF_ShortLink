package org.skillfactory.sf_shortlink.model.entity;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.List;
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

    @Column(unique = true)
    private String shortUrl;

    private Integer maxClicks;

    private Integer clicksCount;

    private LocalDateTime expirationDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "shortenedLink", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserNotification> userNotifications;

}
