package com.unveil.controller;

import com.unveil.dto.ArtworkDto;
import com.unveil.dto.ArtworkHistoryResponse;
import com.unveil.service.ArtworkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for artwork-related endpoints
 */
@Slf4j
@RestController
@RequestMapping("/api/artworks")
public class ArtworkController {

    private final ArtworkService artworkService;

    public ArtworkController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    /**
     * GET /api/artworks/random
     * Get a random artwork from one of the configured museum APIs
     *
     * @return Random artwork data
     */
    @GetMapping("/random")
    public ResponseEntity<ArtworkDto> getRandomArtwork(
            @RequestParam(required = false) String userId) {
        log.info("Request received: GET /api/artworks/random, userId: {}", userId);

        try {
            ArtworkDto artwork;
            if (userId != null && !userId.trim().isEmpty()) {
                // Get unseen artwork for the user
                artwork = artworkService.getRandomArtworkForUser(userId);
            } else {
                // Get any random artwork (no duplicate check)
                artwork = artworkService.getRandomArtwork();
            }
            return ResponseEntity.ok(artwork);
        } catch (Exception e) {
            log.error("Error fetching random artwork: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /**
     * GET /api/artworks/history
     * Get viewing history for a user
     *
     * @param userId User identifier (from header or default to "anonymous")
     * @param limit Maximum number of items to return (default: 50, max: 100)
     * @return User's viewing history
     */
    @GetMapping("/history")
    public ResponseEntity<ArtworkHistoryResponse> getHistory(
            @RequestHeader(value = "X-User-Id", required = false, defaultValue = "anonymous") String userId,
            @RequestParam(required = false) Integer limit) {

        log.info("Request received: GET /api/artworks/history for user: {}, limit: {}", userId, limit);

        try {
            ArtworkHistoryResponse history = artworkService.getViewHistory(userId, limit);
            return ResponseEntity.ok(history);
        } catch (Exception e) {
            log.error("Error fetching history for user {}: {}", userId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Artwork API is running");
    }
}
