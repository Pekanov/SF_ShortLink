package org.skillfactory.sf_shortlink.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class DeleteLinkDTO {
    private UUID userId;
    private String shortUrl;
    private String originalUrl;
}