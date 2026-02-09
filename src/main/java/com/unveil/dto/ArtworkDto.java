package com.unveil.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtworkDto {

    private String artworkId;
    private String title;
    private String artist;
    private String imageUrl;
    private String museumSource;
    private String year;
    private String description;
    private String objectDate;
    private String medium;
    private String dimensions;
    private String creditLine;
}
