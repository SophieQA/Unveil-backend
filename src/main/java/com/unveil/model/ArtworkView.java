package com.unveil.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "artwork_views", indexes = {
    @Index(name = "idx_user_id", columnList = "userId"),
    @Index(name = "idx_artwork_id", columnList = "artworkId"),
    @Index(name = "idx_created_at", columnList = "createdAt")
})
public class ArtworkView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "artwork_id", nullable = false)
    private String artworkId;

    @Column(length = 1000)
    private String title;

    @Column(length = 500)
    private String artist;

    @Column(name = "image_url", length = 2000)
    private String imageUrl;

    @Column(name = "museum_source", length = 50)
    private String museumSource;

    @Column(name = "object_date", length = 100)
    private String objectDate;

    @Column(length = 1000)
    private String medium;

    @Column(length = 500)
    private String dimensions;

    @Column(name = "credit_line", length = 1000)
    private String creditLine;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
