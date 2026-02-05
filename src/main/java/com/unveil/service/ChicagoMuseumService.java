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
 * Service for fetching artworks from Art Institute of Chicago API
 * API Documentation: https://api.artic.edu/docs/
 */
@Slf4j
@Service
public class ChicagoMuseumService {

    private final MuseumApiConfig museumConfig;
    private final RestTemplate restTemplate;
    private final Random random = new Random();

    public ChicagoMuseumService(MuseumApiConfig museumConfig, RestTemplate restTemplate) {
        this.museumConfig = museumConfig;
        this.restTemplate = restTemplate;
    }

    public ArtworkDto getRandomArtwork() {
        String baseUrl = museumConfig.getMuseum2().getBaseUrl();

        try {
            // Fetch artworks with images and public domain status - use random page to get variety
            int randomPage = random.nextInt(100) + 1;
            String url = baseUrl + "/artworks?page=" + randomPage +
                        "&limit=20&fields=id,title,artist_display,image_id,date_display,medium_display,dimensions,credit_line,is_public_domain";

            ChicagoSearchResponse response = restTemplate.getForObject(url, ChicagoSearchResponse.class);

            if (response == null || response.getData() == null || response.getData().isEmpty()) {
                throw new RuntimeException("No artworks found from Art Institute of Chicago");
            }

            // Filter artworks that have valid images and are public domain
            List<ChicagoArtwork> artworksWithImages = response.getData().stream()
                    .filter(artwork -> artwork.getImageId() != null &&
                                      artwork.getImageId().length() > 0 &&
                                      artwork.getIsPublicDomain() != null &&
                                      artwork.getIsPublicDomain())
                    .toList();

            if (artworksWithImages.isEmpty()) {
                throw new RuntimeException("No public domain artworks with images found");
            }

            // Select a random artwork
            ChicagoArtwork randomArtwork = artworksWithImages.get(
                    random.nextInt(artworksWithImages.size())
            );

            // Construct image URL
            String imageUrl = response.getConfig().getIiifUrl() + "/" +
                            randomArtwork.getImageId() + "/full/843,/0/default.jpg";

            log.info("Found valid public domain artwork from Chicago: {} (ID: {})",
                    randomArtwork.getTitle(), randomArtwork.getId());

            return ArtworkDto.builder()
                    .artworkId("chicago-" + randomArtwork.getId())
                    .title(randomArtwork.getTitle())
                    .artist(randomArtwork.getArtistDisplay() != null ?
                            randomArtwork.getArtistDisplay() : "Unknown Artist")
                    .imageUrl(imageUrl)
                    .museumSource("Art Institute of Chicago")
                    .objectDate(randomArtwork.getDateDisplay())
                    .medium(randomArtwork.getMediumDisplay())
                    .dimensions(randomArtwork.getDimensions())
                    .creditLine(randomArtwork.getCreditLine())
                    .build();

        } catch (Exception e) {
            log.error("Error fetching from Art Institute of Chicago: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch artwork from Chicago Museum", e);
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class ChicagoSearchResponse {
        private List<ChicagoArtwork> data;
        private ChicagoConfig config;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class ChicagoArtwork {
        private Long id;
        private String title;

        @JsonProperty("artist_display")
        private String artistDisplay;

        @JsonProperty("image_id")
        private String imageId;

        @JsonProperty("is_public_domain")
        private Boolean isPublicDomain;

        @JsonProperty("date_display")
        private String dateDisplay;

        @JsonProperty("medium_display")
        private String mediumDisplay;

        private String dimensions;

        @JsonProperty("credit_line")
        private String creditLine;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class ChicagoConfig {
        @JsonProperty("iiif_url")
        private String iiifUrl;
    }
}
