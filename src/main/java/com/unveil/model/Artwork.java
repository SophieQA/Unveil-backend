package com.unveil.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "artworks", indexes = {
    @Index(name = "idx_artwork_id_col", columnList = "artworkId"),
    @Index(name = "idx_artwork_created_at", columnList = "createdAt"),
    @Index(name = "idx_artwork_title", columnList = "title")
})
public class Artwork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "artwork_id", nullable = false, unique = true, length = 100)
    private String artworkId;

    @Column(length = 1000, nullable = false)
    private String title;

    @Column(length = 500)
    private String artist;

    @Column(name = "image_url", length = 2000)
    private String imageUrl;

    @Column(name = "museum_source", length = 50)
    private String museumSource;

    @Column(length = 50)
    private String year;

    @Column(length = 5000)
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
