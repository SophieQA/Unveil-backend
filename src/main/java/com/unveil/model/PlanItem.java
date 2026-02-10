package com.unveil.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "plan_items", indexes = {
    @Index(name = "idx_plan_user_id", columnList = "user_id"),
    @Index(name = "idx_plan_tour_event", columnList = "tour_event_id")
})
public class PlanItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, length = 100)
    private String userId;

    @Column(name = "tour_event_id", nullable = false)
    private Long tourEventId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tour_event_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TourEvent tourEvent;

    @CreationTimestamp
    @Column(name = "added_at", updatable = false)
    private LocalDateTime addedAt;
}
