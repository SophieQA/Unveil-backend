package com.unveil.service;

import com.unveil.dto.PlanItemDto;
import com.unveil.model.PlanItem;
import com.unveil.model.TourEvent;
import com.unveil.repository.PlanItemRepository;
import com.unveil.repository.TourEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PlanService {

    private final PlanItemRepository planItemRepository;
    private final TourEventRepository tourEventRepository;

    public PlanService(PlanItemRepository planItemRepository,
                      TourEventRepository tourEventRepository) {
        this.planItemRepository = planItemRepository;
        this.tourEventRepository = tourEventRepository;
    }

    @Transactional
    public PlanItemDto addToPlan(String userId, Long tourEventId) {
        // 检查是否已存在
        if (planItemRepository.findByUserIdAndTourEventId(userId, tourEventId).isPresent()) {
            throw new RuntimeException("This item is already in your plan");
        }

        // 验证 tour/event 是否存在
        TourEvent tourEvent = tourEventRepository.findById(tourEventId)
                .orElseThrow(() -> new RuntimeException("Tour/Event not found"));

        PlanItem planItem = PlanItem.builder()
                .userId(userId)
                .tourEventId(tourEventId)
                .build();

        PlanItem saved = planItemRepository.save(planItem);
        log.info("Added tour/event {} to user {}'s plan", tourEventId, userId);

        // Manually set the tourEvent for DTO conversion since it's not eagerly loaded
        return convertToDto(saved, tourEvent);
    }

    @Transactional
    public void removeFromPlan(String userId, Long tourEventId) {
        planItemRepository.deleteByUserIdAndTourEventId(userId, tourEventId);
        log.info("Removed tour/event {} from user {}'s plan", tourEventId, userId);
    }

    public List<PlanItemDto> getUserPlan(String userId) {
        return planItemRepository.findByUserIdOrderByTourEvent_StartTimeAsc(userId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private PlanItemDto convertToDto(PlanItem planItem) {
        TourEvent tourEvent = planItem.getTourEvent();
        if (tourEvent == null) {
            // Fallback: load from repository if not eagerly loaded
            tourEvent = tourEventRepository.findById(planItem.getTourEventId())
                    .orElseThrow(() -> new RuntimeException("TourEvent not found"));
        }
        return convertToDto(planItem, tourEvent);
    }

    private PlanItemDto convertToDto(PlanItem planItem, TourEvent tourEvent) {
        return PlanItemDto.builder()
                .id(planItem.getId())
                .tourEventId(planItem.getTourEventId())
                .type(tourEvent.getType().name())
                .title(tourEvent.getTitle())
                .description(tourEvent.getDescription())
                .startTime(tourEvent.getStartTime())
                .endTime(tourEvent.getEndTime())
                .location(tourEvent.getLocation())
                .addedAt(planItem.getAddedAt())
                .build();
    }
}
