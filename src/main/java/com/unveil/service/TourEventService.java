package com.unveil.service;

import com.unveil.dto.TourEventDto;
import com.unveil.model.PlanItem;
import com.unveil.model.TourEvent;
import com.unveil.repository.PlanItemRepository;
import com.unveil.repository.TourEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TourEventService {

    private final TourEventRepository tourEventRepository;
    private final PlanItemRepository planItemRepository;

    public TourEventService(TourEventRepository tourEventRepository,
                           PlanItemRepository planItemRepository) {
        this.tourEventRepository = tourEventRepository;
        this.planItemRepository = planItemRepository;
    }

    public List<TourEventDto> getToursByDate(LocalDate date, String userId) {
        List<TourEvent> tours = tourEventRepository.findByTypeAndEventDateAndIsActiveTrueOrderByStartTimeAsc(
                TourEvent.TourEventType.TOUR, date);

        Set<Long> userPlanIds = getUserPlanEventIds(userId);

        return tours.stream()
                .map(tour -> convertToDto(tour, userPlanIds.contains(tour.getId())))
                .collect(Collectors.toList());
    }

    public List<TourEventDto> getEventsByDate(LocalDate date, String userId) {
        List<TourEvent> events = tourEventRepository.findByTypeAndEventDateAndIsActiveTrueOrderByStartTimeAsc(
                TourEvent.TourEventType.EVENT, date);

        Set<Long> userPlanIds = getUserPlanEventIds(userId);

        return events.stream()
                .map(event -> convertToDto(event, userPlanIds.contains(event.getId())))
                .collect(Collectors.toList());
    }

    private Set<Long> getUserPlanEventIds(String userId) {
        return planItemRepository.findByUserIdOrderByTourEvent_StartTimeAsc(userId)
                .stream()
                .map(PlanItem::getTourEventId)
                .collect(Collectors.toSet());
    }

    private TourEventDto convertToDto(TourEvent tourEvent, boolean isInPlan) {
        return TourEventDto.builder()
                .id(tourEvent.getId())
                .type(tourEvent.getType().name())
                .title(tourEvent.getTitle())
                .description(tourEvent.getDescription())
                .startTime(tourEvent.getStartTime())
                .endTime(tourEvent.getEndTime())
                .location(tourEvent.getLocation())
                .sourceUrl(tourEvent.getSourceUrl())
                .isInPlan(isInPlan)
                .build();
    }
}
