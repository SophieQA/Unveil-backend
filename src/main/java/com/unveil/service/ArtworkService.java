package com.unveil.service;

import com.unveil.config.MuseumApiConfig;
import com.unveil.dto.ArtworkDetailDto;
import com.unveil.dto.ArtworkDto;
import com.unveil.dto.ArtworkHistoryResponse;
import com.unveil.dto.ViewRequest;
import com.unveil.model.ArtworkView;
import com.unveil.repository.ArtworkViewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
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
    private final ArtworkViewRepository viewRepository;
    private final RestTemplate restTemplate;
    private final Random random = new Random();

    // Individual museum services
    private final MetMuseumService metMuseumService;
    private final ChicagoMuseumService chicagoMuseumService;
    private final ClevelandMuseumService clevelandMuseumService;

    public ArtworkService(
            MuseumApiConfig museumConfig,
            ArtworkViewRepository viewRepository,
            RestTemplate restTemplate,
            MetMuseumService metMuseumService,
            ChicagoMuseumService chicagoMuseumService,
            ClevelandMuseumService clevelandMuseumService) {
        this.museumConfig = museumConfig;
        this.viewRepository = viewRepository;
        this.restTemplate = restTemplate;
        this.metMuseumService = metMuseumService;
        this.chicagoMuseumService = chicagoMuseumService;
        this.clevelandMuseumService = clevelandMuseumService;
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
     * Record that a user viewed an artwork (asynchronous, non-blocking)
     */
    @Async
    @Transactional
    public void recordViewAsync(String userId, ViewRequest viewRequest) {
        try {
            ArtworkView view = new ArtworkView();
            view.setUserId(userId);
            view.setArtworkId(viewRequest.getArtworkId());
            view.setTitle(viewRequest.getTitle());
            view.setArtist(viewRequest.getArtist());
            view.setImageUrl(viewRequest.getImageUrl());
            view.setMuseumSource(viewRequest.getMuseumSource());
            view.setObjectDate(viewRequest.getObjectDate());
            view.setMedium(viewRequest.getMedium());
            view.setDimensions(viewRequest.getDimensions());
            view.setCreditLine(viewRequest.getCreditLine());

            viewRepository.save(view);
            log.info("Recorded view for user {} - artwork {}", userId, viewRequest.getArtworkId());
        } catch (Exception e) {
            log.error("Error recording view for user {}: {}", userId, e.getMessage(), e);
            // Don't throw exception - this is async and shouldn't affect user experience
        }
    }

    /**
     * Get viewing history for a user
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
     * Get artwork detail from viewing history for a user and artwork
     */
    public ArtworkDetailDto getArtworkDetail(String userId, String artworkId) {
        ArtworkView view = viewRepository.findTopByUserIdAndArtworkIdOrderByCreatedAtDesc(userId, artworkId);
        if (view == null) {
            return null;
        }

        return ArtworkDetailDto.builder()
                .artworkId(view.getArtworkId())
                .title(view.getTitle())
                .artist(view.getArtist())
                .imageUrl(view.getImageUrl())
                .museumSource(view.getMuseumSource())
                .objectDate(view.getObjectDate())
                .medium(view.getMedium())
                .dimensions(view.getDimensions())
                .creditLine(view.getCreditLine())
                .viewedAt(view.getCreatedAt())
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
        return ArtworkHistoryResponse.HistoryItem.builder()
                .id(view.getId())
                .artworkId(view.getArtworkId())
                .title(view.getTitle())
                .artist(view.getArtist())
                .imageUrl(view.getImageUrl())
                .museumSource(view.getMuseumSource())
                .objectDate(view.getObjectDate())
                .medium(view.getMedium())
                .dimensions(view.getDimensions())
                .creditLine(view.getCreditLine())
                .viewedAt(view.getCreatedAt())
                .build();
    }
}
