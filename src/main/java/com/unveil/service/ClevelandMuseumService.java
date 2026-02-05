package com.unveil.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unveil.config.MuseumApiConfig;
import com.unveil.dto.ArtworkDto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

/**
 * Service for fetching artworks from Cleveland Museum of Art API
 * API Documentation: https://openaccess-api.clevelandart.org/
 */
@Slf4j
@Service
public class ClevelandMuseumService {

    private final MuseumApiConfig museumConfig;
    private final RestTemplate restTemplate;
    private final Random random = new Random();

    public ClevelandMuseumService(MuseumApiConfig museumConfig, RestTemplate restTemplate) {
        this.museumConfig = museumConfig;
        this.restTemplate = restTemplate;
    }

    public ArtworkDto getRandomArtwork() {
        String baseUrl = museumConfig.getMuseum3().getBaseUrl();

        try {
            // Fetch artworks with images - use random skip value for variety
            // Cleveland Museum's Open Access collection - all artworks are free to use
            int randomSkip = random.nextInt(5000);
            String url = baseUrl + "?has_image=1&limit=20&skip=" + randomSkip;

            ClevelandSearchResponse response = restTemplate.getForObject(url, ClevelandSearchResponse.class);

            if (response == null || response.getData() == null || response.getData().isEmpty()) {
                throw new RuntimeException("No artworks found from Cleveland Museum of Art");
            }

            // Filter artworks that have valid images with proper URLs
            List<ClevelandArtwork> artworksWithImages = response.getData().stream()
                    .filter(artwork -> artwork.getImages() != null &&
                                      artwork.getImages().getWeb() != null &&
                                      artwork.getImages().getWeb().getUrl() != null &&
                                      !artwork.getImages().getWeb().getUrl().isEmpty() &&
                                      artwork.getImages().getWeb().getUrl().startsWith("http"))
                    .toList();

            if (artworksWithImages.isEmpty()) {
                throw new RuntimeException("No artworks with valid image URLs found");
            }

            // Select a random artwork
            ClevelandArtwork randomArtwork = artworksWithImages.get(
                    random.nextInt(artworksWithImages.size())
            );

            // Get creators/artists
            String artist = "Unknown Artist";
            if (randomArtwork.getCreators() != null && !randomArtwork.getCreators().isEmpty()) {
                artist = randomArtwork.getCreators().get(0).getDescription();
            }

            log.info("Found valid Open Access artwork from Cleveland: {} (ID: {})",
                    randomArtwork.getTitle(), randomArtwork.getId());

            return ArtworkDto.builder()
                    .artworkId("cleveland-" + randomArtwork.getId())
                    .title(randomArtwork.getTitle())
                    .artist(artist)
                    .imageUrl(randomArtwork.getImages().getWeb().getUrl())
                    .museumSource("Cleveland Museum of Art")
                    .objectDate(randomArtwork.getCreationDate())
                    .medium(randomArtwork.getTechnique())
                    .dimensions(randomArtwork.getDimensions())
                    .creditLine(randomArtwork.getCreditLine())
                    .build();

        } catch (Exception e) {
            log.error("Error fetching from Cleveland Museum: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch artwork from Cleveland Museum", e);
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class ClevelandSearchResponse {
        private List<ClevelandArtwork> data;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class ClevelandArtwork {
        private Long id;
        private String title;

        private List<Creator> creators;

        @JsonProperty("creation_date")
        private String creationDate;

        private String technique;
        private String dimensions;

        @JsonProperty("creditline")
        private String creditLine;

        private Images images;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Creator {
        private String description;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Images {
        private ImageDetail web;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class ImageDetail {
        private String url;
    }
}
