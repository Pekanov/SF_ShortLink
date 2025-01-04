package org.skillfactory.sf_shortlink.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateShortLinkDTO {
    private UUID userId;

    private String shortLink;
    private Integer maxClicks;
    private Integer durationDay;
}