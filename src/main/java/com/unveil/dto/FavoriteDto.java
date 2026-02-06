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
public class FavoriteDto {

    private Long id;
    private String userId;
    private String artworkId;
    private LocalDateTime createdAt;
}
