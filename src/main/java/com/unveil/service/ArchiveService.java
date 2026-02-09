package com.unveil.service;

import com.unveil.dto.ArchiveResponse;
import com.unveil.dto.ArchiveViewResponse;
import com.unveil.dto.ArtworkArchiveDto;
import com.unveil.dto.ArtworkArchiveViewDto;
import com.unveil.model.Artwork;
import com.unveil.model.ArtworkView;
import com.unveil.repository.ArtworkRepository;
import com.unveil.repository.ArtworkViewRepository;
import com.unveil.repository.FavoriteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArchiveService {

    private final ArtworkRepository artworkRepository;
    private final ArtworkViewRepository artworkViewRepository;
    private final FavoriteRepository favoriteRepository;

    public ArchiveService(
            ArtworkRepository artworkRepository,
            ArtworkViewRepository artworkViewRepository,
            FavoriteRepository favoriteRepository) {
        this.artworkRepository = artworkRepository;
        this.artworkViewRepository = artworkViewRepository;
        this.favoriteRepository = favoriteRepository;
    }

    /**
     * Get user's artwork archive (viewing history) with favorite status
     * Returns unique artworks with pagination
     */
    public ArchiveViewResponse getArchive(String userId, Integer page, Integer limit) {
        int pageNum = page != null && page > 0 ? page - 1 : 0;
        int pageSize = limit != null && limit > 0 ? Math.min(limit, 100) : 50;

        Pageable pageable = PageRequest.of(pageNum, pageSize);

        log.info("Fetching archive for user: {}, page: {}, limit: {}", userId, pageNum + 1, pageSize);

        // Get user's viewing history
        List<ArtworkView> views = artworkViewRepository.findRecentViewsByUserId(userId, pageable);
        long totalCount = artworkViewRepository.countByUserId(userId);

        // Convert to DTOs with favorite status
        List<ArtworkArchiveViewDto> artworks = views.stream()
            .map(view -> toArchiveViewDto(view, userId))
            .collect(Collectors.toList());

        return ArchiveViewResponse.builder()
            .artworks(artworks)
            .totalCount((int) totalCount)
            .currentPage(pageNum + 1)
            .pageSize(pageSize)
            .totalPages((int) Math.ceil((double) totalCount / pageSize))
            .build();
    }

    /**
     * Search artworks in the archive with optional query
     */
    public ArchiveResponse searchArchive(String query, Integer page, Integer limit) {
        int pageNum = page != null && page > 0 ? page - 1 : 0;
        int pageSize = limit != null && limit > 0 ? Math.min(limit, 100) : 20;

        Pageable pageable = PageRequest.of(pageNum, pageSize);

        Page<Artwork> artworkPage;
        if (query != null && !query.trim().isEmpty()) {
            artworkPage = artworkRepository.searchByQuery(query.trim(), pageable);
            log.info("Searching archive with query: '{}', page: {}, limit: {}", query, pageNum + 1, pageSize);
        } else {
            artworkPage = artworkRepository.findAllByOrderByCreatedAtDesc(pageable);
            log.info("Fetching all artworks from archive, page: {}, limit: {}", pageNum + 1, pageSize);
        }

        List<ArtworkArchiveDto> artworks = artworkPage.getContent().stream()
                .map(this::toArtworkArchiveDto)
                .collect(Collectors.toList());

        return ArchiveResponse.builder()
                .artworks(artworks)
                .totalCount((int) artworkPage.getTotalElements())
                .currentPage(pageNum + 1)
                .pageSize(pageSize)
                .totalPages(artworkPage.getTotalPages())
                .build();
    }

    /**
     * Add an artwork to the archive
     */
    public ArtworkArchiveDto addToArchive(String artworkId, String title, String artist,
                                          String imageUrl, String museumSource, String year, String description) {
        log.info("Adding artwork to archive: {} ({})", title, artworkId);

        // Check if artwork already exists
        if (artworkRepository.findByArtworkId(artworkId).isPresent()) {
            log.warn("Artwork {} already exists in archive", artworkId);
            return toArtworkArchiveDto(artworkRepository.findByArtworkId(artworkId).get());
        }

        Artwork artwork = Artwork.builder()
                .artworkId(artworkId)
                .title(title)
                .artist(artist)
                .imageUrl(imageUrl)
                .museumSource(museumSource)
                .year(year)
                .description(description)
                .build();

        Artwork saved = artworkRepository.save(artwork);
        return toArtworkArchiveDto(saved);
    }

    /**
     * Convert ArtworkView to ArchiveViewDto with favorite status
     */
    private ArtworkArchiveViewDto toArchiveViewDto(ArtworkView view, String userId) {
        // Check if this artwork is favorited
        boolean isFavorited = favoriteRepository.existsByUserIdAndArtworkId(userId, view.getArtworkId());

        // Get artwork details from the joined Artwork entity
        String title = view.getArtwork() != null ? view.getArtwork().getTitle() : "Unknown";
        String artist = view.getArtwork() != null ? view.getArtwork().getArtist() : "Unknown";
        String imageUrl = view.getArtwork() != null ? view.getArtwork().getImageUrl() : null;
        String museumSource = view.getArtwork() != null ? view.getArtwork().getMuseumSource() : null;
        String year = view.getArtwork() != null ? view.getArtwork().getYear() : null;
        String description = view.getArtwork() != null ? view.getArtwork().getDescription() : null;

        return ArtworkArchiveViewDto.builder()
            .id(view.getId())
            .artworkId(view.getArtworkId())
            .title(title)
            .artist(artist)
            .imageUrl(imageUrl)
            .museumSource(museumSource)
            .year(year)
            .description(description)
            .viewedAt(view.getCreatedAt())
            .isFavorited(isFavorited)
            .build();
    }

    private ArtworkArchiveDto toArtworkArchiveDto(Artwork artwork) {
        return ArtworkArchiveDto.builder()
                .id(artwork.getId())
                .artworkId(artwork.getArtworkId())
                .title(artwork.getTitle())
                .artist(artwork.getArtist())
                .imageUrl(artwork.getImageUrl())
                .museumSource(artwork.getMuseumSource())
                .year(artwork.getYear())
                .description(artwork.getDescription())
                .createdAt(artwork.getCreatedAt())
                .build();
    }
}
