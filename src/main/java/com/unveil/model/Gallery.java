package com.unveil.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "galleries", indexes = {
    @Index(name = "idx_gallery_number", columnList = "gallery_number"),
    @Index(name = "idx_gallery_floor", columnList = "floor")
})
public class Gallery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gallery_number", nullable = false, unique = true, length = 50)
    private String galleryNumber;

    @Column(name = "gallery_name", length = 255)
    private String galleryName;

    @Column(nullable = false, length = 20)
    private String floor;

    @Column(name = "x_coordinate", precision = 10, scale = 6)
    private BigDecimal xCoordinate;

    @Column(name = "y_coordinate", precision = 10, scale = 6)
    private BigDecimal yCoordinate;

    @Column(name = "polygon_data", columnDefinition = "jsonb")
    private String polygonData;

    @Column(columnDefinition = "TEXT")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
