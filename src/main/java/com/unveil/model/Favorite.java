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
@Table(name = "favorites", indexes = {
    @Index(name = "idx_user_id_artwork", columnList = "userId,artworkId", unique = true),
    @Index(name = "idx_user_id", columnList = "userId"),
    @Index(name = "idx_created_at", columnList = "createdAt")
})
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, length = 100)
    private String userId;

    @Column(name = "artwork_id", nullable = false, length = 100)
    private String artworkId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "artwork_id", referencedColumnName = "artwork_id", insertable = false, updatable = false)
    private Artwork artwork;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
