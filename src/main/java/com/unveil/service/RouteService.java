package com.unveil.service;

import com.unveil.dto.GalleryLocationDto;
import com.unveil.dto.RoutePoint;
import com.unveil.dto.RouteResponse;
import com.unveil.model.Artwork;
import com.unveil.model.Favorite;
import com.unveil.repository.FavoriteRepository;
import com.unveil.repository.GalleryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for generating AI-powered tour routes based on user's favorite artworks
 * Uses a simple nearest-neighbor algorithm for route optimization
 */
@Slf4j
@Service
public class RouteService {

    private final FavoriteRepository favoriteRepository;
    private final GalleryRepository galleryRepository;

    public RouteService(FavoriteRepository favoriteRepository, GalleryRepository galleryRepository) {
        this.favoriteRepository = favoriteRepository;
        this.galleryRepository = galleryRepository;
    }

    /**
     * Generate an optimized route for visiting user's favorite artworks
     * Algorithm: Group by floor → Sort by coordinates → Generate path segments
     * 
     * @param userId User ID
     * @return RouteResponse with floor-by-floor route information
     */
    public RouteResponse generateRoute(String userId) {
        log.info("Generating route for user: {}", userId);

        // 1. Get user's favorites with gallery information
        List<Favorite> favorites = favoriteRepository.findByUserId(userId);
        
        if (favorites.isEmpty()) {
            log.warn("No favorites found for user: {}", userId);
            return RouteResponse.builder()
                    .floors(new ArrayList<>())
                    .totalArtworks(0)
                    .estimatedDuration("0 min")
                    .build();
        }

        // 2. Extract artworks with gallery numbers
        List<Artwork> artworksWithGalleries = favorites.stream()
                .map(Favorite::getArtwork)
                .filter(artwork -> artwork.getGalleryNumber() != null && !artwork.getGalleryNumber().isEmpty())
                .toList();

        if (artworksWithGalleries.isEmpty()) {
            log.warn("No artworks with gallery information found for user: {}", userId);
            return RouteResponse.builder()
                    .floors(new ArrayList<>())
                    .totalArtworks(0)
                    .estimatedDuration("0 min")
                    .build();
        }

        // 3. Get unique gallery numbers and load their locations
        Set<String> galleryNumbers = artworksWithGalleries.stream()
                .map(Artwork::getGalleryNumber)
                .collect(Collectors.toSet());

        Map<String, GalleryLocationDto> galleryLocations = galleryRepository
                .findAll()
                .stream()
                .filter(gallery -> galleryNumbers.contains(gallery.getGalleryNumber()))
                .collect(Collectors.toMap(
                        gallery -> gallery.getGalleryNumber(),
                        gallery -> GalleryLocationDto.builder()
                                .galleryNumber(gallery.getGalleryNumber())
                                .galleryName(gallery.getGalleryName())
                                .floor(gallery.getFloor())
                                .xCoordinate(gallery.getXCoordinate() != null ? gallery.getXCoordinate().doubleValue() : 0.0)
                                .yCoordinate(gallery.getYCoordinate() != null ? gallery.getYCoordinate().doubleValue() : 0.0)
                                .build()
                ));

        // 4. Group galleries by floor
        Map<String, List<GalleryLocationDto>> galleriesByFloor = galleryLocations.values().stream()
                .collect(Collectors.groupingBy(GalleryLocationDto::getFloor));

        // 5. Generate floor routes
        List<RouteResponse.FloorRoute> floorRoutes = galleriesByFloor.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // Sort by floor number
                .map(entry -> generateFloorRoute(entry.getKey(), entry.getValue()))
                .toList();

        // 6. Calculate estimated duration (assume 5 min per artwork)
        int totalArtworks = artworksWithGalleries.size();
        int estimatedMinutes = totalArtworks * 5;
        String estimatedDuration = formatDuration(estimatedMinutes);

        return RouteResponse.builder()
                .floors(floorRoutes)
                .totalArtworks(totalArtworks)
                .estimatedDuration(estimatedDuration)
                .build();
    }

    /**
     * Generate optimized route for a single floor
     * Uses simple nearest-neighbor algorithm
     */
    private RouteResponse.FloorRoute generateFloorRoute(String floor, List<GalleryLocationDto> galleries) {
        if (galleries.isEmpty()) {
            return RouteResponse.FloorRoute.builder()
                    .floor(floor)
                    .points(new ArrayList<>())
                    .pathSegments(new ArrayList<>())
                    .build();
        }

        // Sort galleries using nearest-neighbor algorithm
        List<GalleryLocationDto> sortedGalleries = sortByNearestNeighbor(galleries);

        // Convert to RoutePoints
        List<RoutePoint> points = sortedGalleries.stream()
                .map(gallery -> RoutePoint.builder()
                        .galleryNumber(gallery.getGalleryNumber())
                        .galleryName(gallery.getGalleryName())
                        .xCoordinate(gallery.getXCoordinate())
                        .yCoordinate(gallery.getYCoordinate())
                        .build())
                .toList();

        // Generate path segments (connections between consecutive points)
        List<RouteResponse.PathSegment> pathSegments = new ArrayList<>();
        for (int i = 0; i < points.size() - 1; i++) {
            pathSegments.add(RouteResponse.PathSegment.builder()
                    .start(points.get(i))
                    .end(points.get(i + 1))
                    .build());
        }

        return RouteResponse.FloorRoute.builder()
                .floor(floor)
                .points(points)
                .pathSegments(pathSegments)
                .build();
    }

    /**
     * Sort galleries using nearest-neighbor algorithm (greedy TSP approximation)
     * Starts from leftmost-topmost gallery and always picks nearest unvisited gallery
     */
    private List<GalleryLocationDto> sortByNearestNeighbor(List<GalleryLocationDto> galleries) {
        if (galleries.size() <= 1) {
            return new ArrayList<>(galleries);
        }

        List<GalleryLocationDto> unvisited = new ArrayList<>(galleries);
        List<GalleryLocationDto> route = new ArrayList<>();

        // Start from the gallery with minimum (x + y) - typically top-left
        GalleryLocationDto current = unvisited.stream()
                .min(Comparator.comparingDouble(g -> g.getXCoordinate() + g.getYCoordinate()))
                .orElse(unvisited.get(0));

        route.add(current);
        unvisited.remove(current);

        // Nearest-neighbor: always pick the closest unvisited gallery
        while (!unvisited.isEmpty()) {
            final GalleryLocationDto from = current;
            current = unvisited.stream()
                    .min(Comparator.comparingDouble(g -> calculateDistance(from, g)))
                    .orElse(unvisited.get(0));

            route.add(current);
            unvisited.remove(current);
        }

        return route;
    }

    /**
     * Calculate Euclidean distance between two galleries
     */
    private double calculateDistance(GalleryLocationDto from, GalleryLocationDto to) {
        double dx = to.getXCoordinate() - from.getXCoordinate();
        double dy = to.getYCoordinate() - from.getYCoordinate();
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Format duration in human-readable format
     */
    private String formatDuration(int minutes) {
        if (minutes < 60) {
            return minutes + " min";
        }
        int hours = minutes / 60;
        int remainingMinutes = minutes % 60;
        if (remainingMinutes == 0) {
            return hours + " hr";
        }
        return hours + " hr " + remainingMinutes + " min";
    }
}
