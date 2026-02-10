package com.unveil.repository;

import com.unveil.model.PlanItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanItemRepository extends JpaRepository<PlanItem, Long> {
    List<PlanItem> findByUserIdOrderByTourEvent_StartTimeAsc(String userId);

    Optional<PlanItem> findByUserIdAndTourEventId(String userId, Long tourEventId);

    void deleteByUserIdAndTourEventId(String userId, Long tourEventId);
}
