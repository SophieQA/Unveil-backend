package com.unveil.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response for Archive page with favorite status
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArchiveViewResponse {

    private List<ArtworkArchiveViewDto> artworks;
    private int totalCount;
    private int currentPage;
    private int pageSize;
    private int totalPages;
}
