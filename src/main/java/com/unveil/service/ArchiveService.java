package com.unveil.service;

import com.unveil.dto.ArchiveResponse;
import com.unveil.dto.ArtworkArchiveDto;
import com.unveil.model.Artwork;
import com.unveil.repository.ArtworkRepository;
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

    public ArchiveService(ArtworkRepository artworkRepository) {
        this.artworkRepository = artworkRepository;
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
