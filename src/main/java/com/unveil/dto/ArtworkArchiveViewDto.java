package com.unveil.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for artwork in Archive view
 * Includes whether it's favorited by the current user
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtworkArchiveViewDto {

    private Long id;
    private String artworkId;
    private String title;
    private String artist;
    private String imageUrl;
    private String museumSource;
    private String year;
    private String description;
    private LocalDateTime viewedAt;

    // Whether this artwork is in the user's favorites
    private Boolean isFavorited;
}
