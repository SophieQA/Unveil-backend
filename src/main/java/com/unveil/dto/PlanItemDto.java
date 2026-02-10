package com.unveil.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanItemDto {
    private Long id;
    private Long tourEventId; // For deletion: DELETE /api/tour-plan/my-plan/{userId}/items/{tourEventId}
    private String type;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private LocalDateTime addedAt;
}
