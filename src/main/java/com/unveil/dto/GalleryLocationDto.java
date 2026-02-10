package com.unveil.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GalleryLocationDto {
    private String galleryNumber;
    private String galleryName;
    private String floor;
    private Double xCoordinate;
    private Double yCoordinate;
    private String polygonData;
}
