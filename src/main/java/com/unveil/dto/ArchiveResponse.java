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
public class ArchiveResponse {

    private List<ArtworkArchiveDto> artworks;
    private Integer totalCount;
    private Integer currentPage;
    private Integer pageSize;
    private Integer totalPages;
}
