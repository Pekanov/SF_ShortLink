package org.skillfactory.sf_shortlink.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_notification")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "link_id", nullable = false)
    private ShortenedLink shortenedLink;



}
