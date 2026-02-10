package com.unveil.controller;

import com.unveil.dto.GalleryLocationDto;
import com.unveil.dto.RouteResponse;
import com.unveil.service.GalleryService;
import com.unveil.service.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/galleries")
@CrossOrigin(origins = "*")
public class GalleryController {

    private final GalleryService galleryService;
    private final RouteService routeService;

    public GalleryController(GalleryService galleryService, RouteService routeService) {
        this.galleryService = galleryService;
        this.routeService = routeService;
    }

    /**
     * 获取画廊位置信息
     * GET /api/galleries/{galleryNumber}
     */
    @GetMapping("/{galleryNumber}")
    public ResponseEntity<GalleryLocationDto> getGalleryLocation(
            @PathVariable String galleryNumber) {

        return galleryService.getGalleryLocation(galleryNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 按楼层获取所有画廊
     * GET /api/galleries/floor/{floor}
     */
    @GetMapping("/floor/{floor}")
    public ResponseEntity<List<GalleryLocationDto>> getGalleriesByFloor(
            @PathVariable String floor) {

        List<GalleryLocationDto> galleries = galleryService.getGalleriesByFloor(floor);
        return ResponseEntity.ok(galleries);
    }

    /**
     * Generate user's artwork tour route
     * GET /api/galleries/route/{userId}
     */
    @GetMapping("/route/{userId}")
    public ResponseEntity<RouteResponse> generateRoute(@PathVariable String userId) {
        log.info("Generating route for user: {}", userId);
        try {
            RouteResponse route = routeService.generateRoute(userId);
            return ResponseEntity.ok(route);
        } catch (Exception e) {
            log.error("Error generating route for user: {}", userId, e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
