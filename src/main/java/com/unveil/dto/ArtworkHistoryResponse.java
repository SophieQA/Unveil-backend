package com.unveil.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtworkHistoryResponse {

    private List<HistoryItem> items;
    private Integer totalCount;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HistoryItem {
        private Long id;
        private String artworkId;
        private String title;
        private String artist;
        private String imageUrl;
        private String museumSource;
        private String objectDate;
        private String medium;
        private String dimensions;
        private String creditLine;
        private LocalDateTime viewedAt;
    }
}
