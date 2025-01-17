package org.skillfactory.sf_shortlink.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateShortLinkDTO {
    private UUID userId;
    private String originalUrl;
    private Integer maxClicks;
    private Integer durationDay;
}
