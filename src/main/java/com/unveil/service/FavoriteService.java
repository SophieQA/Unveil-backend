package com.unveil.service;

import com.unveil.dto.ArtworkArchiveDto;
import com.unveil.dto.FavoriteDto;
import com.unveil.dto.FavoritesResponse;
import com.unveil.dto.FavoriteWithArtworkDto;
import com.unveil.model.Favorite;
import com.unveil.repository.FavoriteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    /**
     * Add a favorite artwork for a user
     */
    @Transactional
    public FavoriteDto addFavorite(String userId, String artworkId) {
        log.info("Adding favorite for user: {}, artwork: {}", userId, artworkId);

        // Check if already favorited
        if (favoriteRepository.existsByUserIdAndArtworkId(userId, artworkId)) {
            log.warn("Artwork {} already favorited by user {}", artworkId, userId);
            Favorite existing = favoriteRepository.findByUserIdAndArtworkId(userId, artworkId).orElseThrow();
            return toFavoriteDto(existing);
        }

        Favorite favorite = Favorite.builder()
                .userId(userId)
                .artworkId(artworkId)
                .build();

        Favorite saved = favoriteRepository.save(favorite);
        log.info("Favorite added successfully for user: {}, artwork: {}", userId, artworkId);
        return toFavoriteDto(saved);
    }

    /**
     * Get all favorites for a user with artwork information
     */
    public FavoritesResponse getFavorites(String userId, Integer page, Integer limit) {
        int pageNum = page != null && page > 0 ? page - 1 : 0;
        int pageSize = limit != null && limit > 0 ? Math.min(limit, 100) : 50;

        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Favorite> favoritePage = favoriteRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);

        log.info("Fetching favorites for user: {}, page: {}, limit: {}", userId, pageNum + 1, pageSize);

        List<FavoriteWithArtworkDto> favorites = favoritePage.getContent().stream()
                .map(this::toFavoriteWithArtworkDto)
                .collect(Collectors.toList());

        return FavoritesResponse.builder()
                .favorites(favorites)
                .totalCount((int) favoritePage.getTotalElements())
                .currentPage(pageNum + 1)
                .pageSize(pageSize)
                .totalPages(favoritePage.getTotalPages())
                .build();
    }

    /**
     * Remove a favorite artwork for a user
     */
    @Transactional
    public void removeFavorite(String userId, String artworkId) {
        log.info("Removing favorite for user: {}, artwork: {}", userId, artworkId);

        if (!favoriteRepository.existsByUserIdAndArtworkId(userId, artworkId)) {
            log.warn("Favorite not found for user: {}, artwork: {}", userId, artworkId);
            return;
        }

        favoriteRepository.deleteByUserIdAndArtworkId(userId, artworkId);
        log.info("Favorite removed successfully for user: {}, artwork: {}", userId, artworkId);
    }


    private FavoriteDto toFavoriteDto(Favorite favorite) {
        return FavoriteDto.builder()
                .id(favorite.getId())
                .userId(favorite.getUserId())
                .artworkId(favorite.getArtworkId())
                .createdAt(favorite.getCreatedAt())
                .build();
    }

    private FavoriteWithArtworkDto toFavoriteWithArtworkDto(Favorite favorite) {
        ArtworkArchiveDto artworkDto = null;
        if (favorite.getArtwork() != null) {
            artworkDto = ArtworkArchiveDto.builder()
                    .id(favorite.getArtwork().getId())
                    .artworkId(favorite.getArtwork().getArtworkId())
                    .title(favorite.getArtwork().getTitle())
                    .artist(favorite.getArtwork().getArtist())
                    .imageUrl(favorite.getArtwork().getImageUrl())
                    .museumSource(favorite.getArtwork().getMuseumSource())
                    .year(favorite.getArtwork().getYear())
                    .description(favorite.getArtwork().getDescription())
                    .createdAt(favorite.getArtwork().getCreatedAt())
                    .galleryNumber(favorite.getArtwork().getGalleryNumber())
                    .build();
        }

        return FavoriteWithArtworkDto.builder()
                .id(favorite.getId())
                .userId(favorite.getUserId())
                .artworkId(favorite.getArtworkId())
                .createdAt(favorite.getCreatedAt())
                .artwork(artworkDto)
                .build();
    }
}
