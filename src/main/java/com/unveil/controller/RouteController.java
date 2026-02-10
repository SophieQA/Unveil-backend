package com.unveil.controller;

import com.unveil.dto.RouteResponse;
import com.unveil.service.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for AI-powered tour route generation
 */
@Slf4j
@RestController
@RequestMapping("/api/routes")
@CrossOrigin(origins = "*")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    /**
     * Generate an optimized tour route based on user's favorite artworks
     * 
     * GET /api/routes/generate?userId={userId}
     * 
     * @param userId User ID (can be from header or query param)
     * @return RouteResponse with floor-by-floor route information
     */
    @GetMapping("/generate")
    public ResponseEntity<RouteResponse> generateRoute(
            @RequestParam(required = false) String userId,
            @RequestHeader(value = "X-User-Id", required = false) String headerUserId) {
        
        // Use userId from query param or header
        String effectiveUserId = userId != null ? userId : headerUserId;
        
        if (effectiveUserId == null || effectiveUserId.isBlank()) {
            log.warn("Route generation attempted without user ID");
            return ResponseEntity.badRequest().build();
        }

        log.info("Generating route for user: {}", effectiveUserId);
        
        try {
            RouteResponse route = routeService.generateRoute(effectiveUserId);
            return ResponseEntity.ok(route);
        } catch (Exception e) {
            log.error("Error generating route for user: {}", effectiveUserId, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Alternative endpoint that matches the existing pattern
     * GET /api/routes/{userId}
     */
    @GetMapping("/{userId}")
    public ResponseEntity<RouteResponse> generateRouteByPath(@PathVariable String userId) {
        log.info("Generating route for user (path param): {}", userId);
        
        try {
            RouteResponse route = routeService.generateRoute(userId);
            return ResponseEntity.ok(route);
        } catch (Exception e) {
            log.error("Error generating route for user: {}", userId, e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
