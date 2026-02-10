package com.unveil.repository;

import com.unveil.model.TourEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TourEventRepository extends JpaRepository<TourEvent, Long> {
    List<TourEvent> findByEventDateAndIsActiveTrueOrderByStartTimeAsc(LocalDate eventDate);

    List<TourEvent> findByTypeAndEventDateAndIsActiveTrueOrderByStartTimeAsc(
        TourEvent.TourEventType type, LocalDate eventDate);
}
