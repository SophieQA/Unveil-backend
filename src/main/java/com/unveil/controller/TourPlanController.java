package com.unveil.controller;

import com.unveil.dto.PlanItemDto;
import com.unveil.dto.TourEventDto;
import com.unveil.service.PlanService;
import com.unveil.service.TourEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/tour-plan")
@CrossOrigin(origins = "*")
public class TourPlanController {

    private final TourEventService tourEventService;
    private final PlanService planService;

    public TourPlanController(TourEventService tourEventService, PlanService planService) {
        this.tourEventService = tourEventService;
        this.planService = planService;
    }

    /**
     * 获取指定日期的 Tours
     * GET /api/tour-plan/tours?date=2026-02-10&userId=user123
     */
    @GetMapping("/tours")
    public ResponseEntity<List<TourEventDto>> getTours(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam String userId) {

        List<TourEventDto> tours = tourEventService.getToursByDate(date, userId);
        return ResponseEntity.ok(tours);
    }

    /**
     * 获取指定日期的 Events
     * GET /api/tour-plan/events?date=2026-02-10&userId=user123
     */
    @GetMapping("/events")
    public ResponseEntity<List<TourEventDto>> getEvents(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam String userId) {

        List<TourEventDto> events = tourEventService.getEventsByDate(date, userId);
        return ResponseEntity.ok(events);
    }

    /**
     * 添加到我的计划
     * POST /api/tour-plan/my-plan/{userId}/items
     * Body: { "tourEventId": 123 }
     */
    @PostMapping("/my-plan/{userId}/items")
    public ResponseEntity<PlanItemDto> addToPlan(
            @PathVariable String userId,
            @RequestBody Map<String, Long> request) {

        Long tourEventId = request.get("tourEventId");
        PlanItemDto planItem = planService.addToPlan(userId, tourEventId);
        return ResponseEntity.ok(planItem);
    }

    /**
     * 从我的计划移除
     * DELETE /api/tour-plan/my-plan/{userId}/items/{tourEventId}
     */
    @DeleteMapping("/my-plan/{userId}/items/{tourEventId}")
    public ResponseEntity<Void> removeFromPlan(
            @PathVariable String userId,
            @PathVariable Long tourEventId) {

        planService.removeFromPlan(userId, tourEventId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 获取用户的完整计划（按时间排序）
     * GET /api/tour-plan/my-plan/{userId}
     */
    @GetMapping("/my-plan/{userId}")
    public ResponseEntity<List<PlanItemDto>> getUserPlan(@PathVariable String userId) {
        List<PlanItemDto> plan = planService.getUserPlan(userId);
        return ResponseEntity.ok(plan);
    }
}
