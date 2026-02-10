package com.unveil.service;

import com.unveil.config.MuseumApiConfig;
import com.unveil.dto.ArtworkDto;
import com.unveil.dto.ArtworkHistoryResponse;
import com.unveil.model.Artwork;
import com.unveil.model.ArtworkView;
import com.unveil.repository.ArtworkRepository;
import com.unveil.repository.ArtworkViewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArtworkService {

    private final MuseumApiConfig museumConfig;
    private final ArtworkRepository artworkRepository;
    private final ArtworkViewRepository viewRepository;
    private final RestTemplate restTemplate;
    private final Random random = new Random();

    // Individual museum services
    private final MetMuseumService metMuseumService;
    private final ChicagoMuseumService chicagoMuseumService;
    private final ClevelandMuseumService clevelandMuseumService;

    public ArtworkService(
            MuseumApiConfig museumConfig,
            ArtworkRepository artworkRepository,
            ArtworkViewRepository viewRepository,
            RestTemplate restTemplate,
            MetMuseumService metMuseumService,
            ChicagoMuseumService chicagoMuseumService,
            ClevelandMuseumService clevelandMuseumService) {
        this.museumConfig = museumConfig;
        this.artworkRepository = artworkRepository;
        this.viewRepository = viewRepository;
        this.restTemplate = restTemplate;
        this.metMuseumService = metMuseumService;
        this.chicagoMuseumService = chicagoMuseumService;
        this.clevelandMuseumService = clevelandMuseumService;
    }

    /**
     * Get a random artwork for a user (never seen before)
     * 1. Fetch from API
     * 2. Ensure it exists in artworks table
     * 3. Check if user already viewed it
     * 4. Record view and return
     */
    @Transactional
    public ArtworkDto getRandomArtworkForUser(String userId) {
        log.info("Fetching random artwork for user: {}", userId);

        // Keep trying until we find an unseen artwork
        for (int attempt = 0; attempt < 10; attempt++) {
            try {
                // 1. Get random from API
                ArtworkDto apiArtwork = getRandomArtwork();

                // 2. Sync to artworks table if not exists
                Artwork artwork = artworkRepository.findByArtworkId(apiArtwork.getArtworkId())
                    .orElseGet(() -> {
                        Artwork newArtwork = Artwork.builder()
                            .artworkId(apiArtwork.getArtworkId())
                            .title(apiArtwork.getTitle())
                            .artist(apiArtwork.getArtist())
                            .imageUrl(apiArtwork.getImageUrl())
                            .museumSource(apiArtwork.getMuseumSource())
                            .year(apiArtwork.getYear())
                            .description(apiArtwork.getDescription())
                            .galleryNumber(apiArtwork.getGalleryNumber())
                            .build();
                        return artworkRepository.save(newArtwork);
                    });

                // 3. Check if user already viewed
                boolean viewed = viewRepository.existsByUserIdAndArtworkId(userId, artwork.getArtworkId());

                if (!viewed) {
                    // 4. Record view
                    ArtworkView view = ArtworkView.builder()
                        .userId(userId)
                        .artworkId(artwork.getArtworkId())
                        .build();
                    viewRepository.save(view);

                    log.info("Random artwork generated for user {}: {}", userId, artwork.getArtworkId());
                    return apiArtwork;
                }
                // Else: already viewed, continue loop to fetch another
            } catch (Exception e) {
                log.warn("Attempt {} failed: {}", attempt + 1, e.getMessage());
            }
        }

        throw new RuntimeException("Unable to find unseen artwork after multiple attempts");
    }

    /**
     * Get a random artwork from one of the configured museums
     */
    public ArtworkDto getRandomArtwork() {
        List<String> configuredMuseums = getConfiguredMuseumIdentifiers();

        if (configuredMuseums.isEmpty()) {
            throw new RuntimeException("No museum APIs configured");
        }

        // Randomly select a museum
        String selectedMuseum = configuredMuseums.get(random.nextInt(configuredMuseums.size()));

        log.info("Fetching random artwork from {}", selectedMuseum);

        // Fetch from the selected museum
        try {
            return fetchFromMuseum(selectedMuseum);
        } catch (Exception e) {
            log.error("Error fetching from museum {}: {}", selectedMuseum, e.getMessage());

            // Try another museum if the selected one fails
            configuredMuseums.remove(selectedMuseum);
            if (!configuredMuseums.isEmpty()) {
                String fallbackMuseum = configuredMuseums.get(0);
                log.info("Trying fallback museum: {}", fallbackMuseum);
                return fetchFromMuseum(fallbackMuseum);
            }

            throw new RuntimeException("Failed to fetch artwork from any museum", e);
        }
    }


    /**
     * Get viewing history (archive) for a user
     */
    public ArtworkHistoryResponse getViewHistory(String userId, Integer limit) {
        int pageSize = limit != null && limit > 0 ? Math.min(limit, 100) : 50;
        Pageable pageable = PageRequest.of(0, pageSize);

        List<ArtworkView> views = viewRepository.findRecentViewsByUserId(userId, pageable);
        long totalCount = viewRepository.countByUserId(userId);

        List<ArtworkHistoryResponse.HistoryItem> items = views.stream()
                .map(this::toHistoryItem)
                .collect(Collectors.toList());

        return ArtworkHistoryResponse.builder()
                .items(items)
                .totalCount((int) totalCount)
                .build();
    }

    /**
     * Get list of configured museum identifiers
     */
    private List<String> getConfiguredMuseumIdentifiers() {
        List<String> museums = new ArrayList<>();
        if (museumConfig.getMuseum1().isConfigured()) museums.add("museum1");
        if (museumConfig.getMuseum2().isConfigured()) museums.add("museum2");
        if (museumConfig.getMuseum3().isConfigured()) museums.add("museum3");
        if (museumConfig.getMuseum4().isConfigured() && museumConfig.getMuseum4().hasApiKey()) {
            museums.add("museum4");
        }
        if (museumConfig.getMuseum5().isConfigured() && museumConfig.getMuseum5().hasApiKey()) {
            museums.add("museum5");
        }
        return museums;
    }

    /**
     * Fetch artwork from a specific museum
     */
    private ArtworkDto fetchFromMuseum(String museumIdentifier) {
        return switch (museumIdentifier) {
            case "museum1" -> metMuseumService.getRandomArtwork();
            case "museum2" -> chicagoMuseumService.getRandomArtwork();
            case "museum3" -> clevelandMuseumService.getRandomArtwork();
            default -> throw new IllegalArgumentException("Unknown museum: " + museumIdentifier);
        };
    }

    /**
     * Convert entity to history item DTO
     */
    private ArtworkHistoryResponse.HistoryItem toHistoryItem(ArtworkView view) {
        String title = view.getArtwork() != null ? view.getArtwork().getTitle() : "Unknown";
        String artist = view.getArtwork() != null ? view.getArtwork().getArtist() : "Unknown";
        String imageUrl = view.getArtwork() != null ? view.getArtwork().getImageUrl() : null;
        String museumSource = view.getArtwork() != null ? view.getArtwork().getMuseumSource() : null;

        return ArtworkHistoryResponse.HistoryItem.builder()
                .id(view.getId())
                .artworkId(view.getArtworkId())
                .title(title)
                .artist(artist)
                .imageUrl(imageUrl)
                .museumSource(museumSource)
                .viewedAt(view.getCreatedAt())
                .build();
    }
}


