package com.unveil.controller;

import com.unveil.dto.ArtworkDto;
import com.unveil.dto.ArtworkDetailDto;
import com.unveil.dto.ArtworkHistoryResponse;
import com.unveil.dto.ViewRequest;
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
    public ResponseEntity<ArtworkDto> getRandomArtwork() {
        log.info("Request received: GET /api/artworks/random");

        try {
            ArtworkDto artwork = artworkService.getRandomArtwork();
            return ResponseEntity.ok(artwork);
        } catch (Exception e) {
            log.error("Error fetching random artwork: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * POST /api/artworks/view
     * Record that a user viewed an artwork (asynchronous)
     * This endpoint returns immediately and processes the recording in the background
     *
     * @param userId User identifier (from header or default to "anonymous")
     * @param viewRequest Artwork details to record
     * @return 202 Accepted status
     */
    @PostMapping("/view")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Void> recordView(
            @RequestHeader(value = "X-User-Id", required = false, defaultValue = "anonymous") String userId,
            @RequestBody ViewRequest viewRequest) {

        log.info("Request received: POST /api/artworks/view for user: {}", userId);

        // Async processing - returns immediately
        artworkService.recordViewAsync(userId, viewRequest);

        return ResponseEntity.accepted().build();
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
     * GET /api/artworks/details?userId=&artworkId=
     * Get artwork detail for a user from viewing history
     */
    @GetMapping("/details")
    public ResponseEntity<ArtworkDetailDto> getArtworkDetail(
            @RequestParam String userId,
            @RequestParam String artworkId) {

        log.info("Request received: GET /api/artworks/details for user: {}, artwork: {}", userId, artworkId);

        try {
            ArtworkDetailDto detail = artworkService.getArtworkDetail(userId, artworkId);
            if (detail == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(detail);
        } catch (Exception e) {
            log.error("Error fetching artwork detail for user {}: {}", userId, e.getMessage(), e);
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
