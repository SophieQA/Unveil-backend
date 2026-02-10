package com.unveil.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtworkArchiveDto {

    private Long id;
    private String artworkId;
    private String title;
    private String artist;
    private String imageUrl;
    private String museumSource;
    private String year;
    private String description;
    private LocalDateTime createdAt;
    private String galleryNumber;
}
