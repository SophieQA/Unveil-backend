package com.unveil.controller;

import com.unveil.dto.ArchiveResponse;
import com.unveil.service.ArchiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for artwork archive endpoints
 */
@Slf4j
@RestController
@RequestMapping("/api/artworks/archive")
public class ArchiveController {

    private final ArchiveService archiveService;

    public ArchiveController(ArchiveService archiveService) {
        this.archiveService = archiveService;
    }

    /**
     * GET /api/artworks/archive?query=&page=&limit=
     * Search artworks in the archive
     *
     * @param query Optional search query (searches in title and artist)
     * @param page Page number (1-indexed, default: 1)
     * @param limit Items per page (default: 20, max: 100)
     * @return Archive response with paginated artworks
     */
    @GetMapping
    public ResponseEntity<ArchiveResponse> searchArchive(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit) {

        log.info("Request received: GET /api/artworks/archive with query: {}, page: {}, limit: {}",
                query, page, limit);

        try {
            ArchiveResponse archive = archiveService.searchArchive(query, page, limit);
            return ResponseEntity.ok(archive);
        } catch (Exception e) {
            log.error("Error searching archive: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
