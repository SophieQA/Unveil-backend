package com.unveil.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutePoint {
    private String type; // "artwork", "elevator", "stairs"
    private String galleryNumber;
    private String galleryName;
    private String floor;
    private Double xCoordinate;
    private Double yCoordinate;
    private String artworkId; // Only for artwork points
    private String artworkTitle; // Only for artwork points
    private String artworkImageUrl; // Only for artwork points
    private String description; // For transition points
    private Integer order; // Order in the route
}
