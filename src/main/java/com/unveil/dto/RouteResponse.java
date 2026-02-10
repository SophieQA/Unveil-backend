package com.unveil.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteResponse {
    private List<FloorRoute> floors;
    private Integer totalArtworks;
    private String estimatedDuration;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FloorRoute {
        private String floor;
        private List<RoutePoint> points;
        private List<PathSegment> pathSegments;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PathSegment {
        private RoutePoint start;
        private RoutePoint end;
    }
}
