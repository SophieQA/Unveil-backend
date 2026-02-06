package com.unveil.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoritesResponse {

    private List<FavoriteWithArtworkDto> favorites;
    private Integer totalCount;
    private Integer currentPage;
    private Integer pageSize;
    private Integer totalPages;
}
