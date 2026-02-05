package com.unveil.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewRequest {

    private String artworkId;
    private String title;
    private String artist;
    private String imageUrl;
    private String museumSource;
    private String objectDate;
    private String medium;
    private String dimensions;
    private String creditLine;
}
