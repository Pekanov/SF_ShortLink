package org.skillfactory.sf_shortlink.model.dto;

import lombok.Data;

@Data
public class CreateShortLinkDTO {
    private String originalUrl;
    private Integer maxClicks;
    private Integer durationDay;
}
