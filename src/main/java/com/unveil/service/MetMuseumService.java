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
 * Service for fetching artworks from The Metropolitan Museum of Art API
 * API Documentation: https://metmuseum.github.io/
 */
@Slf4j
@Service
public class MetMuseumService {

    private final MuseumApiConfig museumConfig;
    private final RestTemplate restTemplate;
    private final Random random = new Random();

    public MetMuseumService(MuseumApiConfig museumConfig, RestTemplate restTemplate) {
        this.museumConfig = museumConfig;
        this.restTemplate = restTemplate;
    }

    public ArtworkDto getRandomArtwork() {
        String baseUrl = museumConfig.getMuseum1().getBaseUrl();

        try {
            // Step 1: Get list of object IDs with images from Open Access collection
            // isPublicDomain=true ensures only OA (Open Access) artworks are returned
            // Department 11 = European Paintings
            String searchUrl = baseUrl + "/search?hasImages=true&isPublicDomain=true&departmentId=11&q=painting";
            MetSearchResponse searchResponse = restTemplate.getForObject(searchUrl, MetSearchResponse.class);

            if (searchResponse == null || searchResponse.getObjectIDs() == null ||
                searchResponse.getObjectIDs().isEmpty()) {
                throw new RuntimeException("No artworks found from Met Museum");
            }

            // Step 2: Try to find a valid artwork with image (max 5 attempts)
            List<Long> objectIds = searchResponse.getObjectIDs();
            int maxAttempts = 5;

            for (int attempt = 0; attempt < maxAttempts; attempt++) {
                // Randomly select an object ID
                Long randomObjectId = objectIds.get(random.nextInt(Math.min(objectIds.size(), 1000)));

                // Step 3: Get detailed information about the object
                String objectUrl = baseUrl + "/objects/" + randomObjectId;
                MetObjectResponse objectResponse = restTemplate.getForObject(objectUrl, MetObjectResponse.class);

                // Step 4: Validate the response has a valid image and is public domain
                if (objectResponse != null &&
                    objectResponse.getIsPublicDomain() != null &&
                    objectResponse.getIsPublicDomain() &&
                    objectResponse.getPrimaryImage() != null &&
                    !objectResponse.getPrimaryImage().isEmpty()) {

                    log.info("Found valid Open Access artwork: {} (ID: {})",
                            objectResponse.getTitle(), objectResponse.getObjectID());

                    // Step 5: Convert to ArtworkDto
                    return ArtworkDto.builder()
                            .artworkId("met-" + objectResponse.getObjectID())
                            .title(objectResponse.getTitle())
                            .artist(objectResponse.getArtistDisplayName() != null ?
                                    objectResponse.getArtistDisplayName() : "Unknown Artist")
                            .imageUrl(objectResponse.getPrimaryImage())
                            .museumSource("The Metropolitan Museum of Art")
                            .objectDate(objectResponse.getObjectDate())
                            .medium(objectResponse.getMedium())
                            .dimensions(objectResponse.getDimensions())
                            .creditLine(objectResponse.getCreditLine())
                            .build();
                }

                log.warn("Attempt {} failed - artwork doesn't have valid image or is not public domain", attempt + 1);
            }

            throw new RuntimeException("Failed to find artwork with valid image after " + maxAttempts + " attempts");

        } catch (Exception e) {
            log.error("Error fetching from Met Museum: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch artwork from Met Museum", e);
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class MetSearchResponse {
        private Integer total;
        private List<Long> objectIDs;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class MetObjectResponse {
        private Long objectID;
        private String title;
        private String artistDisplayName;
        private String primaryImage;
        private Boolean isPublicDomain;
        private String objectDate;
        private String medium;
        private String dimensions;
        private String creditLine;
    }
}
