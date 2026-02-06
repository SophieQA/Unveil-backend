package com.unveil.controller;

import com.unveil.dto.FavoriteDto;
import com.unveil.dto.FavoriteRequest;
import com.unveil.dto.FavoritesResponse;
import com.unveil.service.FavoriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for favorite artworks endpoints
 */
@Slf4j
@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    /**
     * POST /api/favorites
     * Add an artwork to user's favorites
     *
     * @param favoriteRequest Contains userId and artworkId
     * @return Created favorite
     */
    @PostMapping
    public ResponseEntity<FavoriteDto> addFavorite(@RequestBody FavoriteRequest favoriteRequest) {
        log.info("Request received: POST /api/favorites for user: {}, artwork: {}",
                favoriteRequest.getUserId(), favoriteRequest.getArtworkId());

        try {
            FavoriteDto favorite = favoriteService.addFavorite(
                    favoriteRequest.getUserId(),
                    favoriteRequest.getArtworkId()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(favorite);
        } catch (Exception e) {
            log.error("Error adding favorite: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/favorites?userId=
     * Get all favorites for a user
     *
     * @param userId User identifier
     * @param page Page number (1-indexed, default: 1)
     * @param limit Items per page (default: 50, max: 100)
     * @return Paginated favorites
     */
    @GetMapping
    public ResponseEntity<FavoritesResponse> getFavorites(
            @RequestParam String userId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit) {

        log.info("Request received: GET /api/favorites for user: {}, page: {}, limit: {}",
                userId, page, limit);

        try {
            FavoritesResponse favorites = favoriteService.getFavorites(userId, page, limit);
            return ResponseEntity.ok(favorites);
        } catch (Exception e) {
            log.error("Error fetching favorites for user {}: {}", userId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * DELETE /api/favorites/:artworkId?userId=
     * Remove an artwork from user's favorites
     *
     * @param artworkId Artwork identifier
     * @param userId User identifier
     * @return No content response
     */
    @DeleteMapping("/{artworkId}")
    public ResponseEntity<Void> removeFavorite(
            @PathVariable String artworkId,
            @RequestParam String userId) {

        log.info("Request received: DELETE /api/favorites/{} for user: {}", artworkId, userId);

        try {
            favoriteService.removeFavorite(userId, artworkId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error removing favorite: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
