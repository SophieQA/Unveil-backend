package com.unveil.repository;

import com.unveil.model.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    /**
     * Find a favorite by user and artwork
     */
    Optional<Favorite> findByUserIdAndArtworkId(String userId, String artworkId);

    /**
     * Check if a favorite exists
     */
    boolean existsByUserIdAndArtworkId(String userId, String artworkId);

    /**
     * Get all favorites for a user with pagination
     */
    Page<Favorite> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);

    /**
     * Count favorites for a user
     */
    long countByUserId(String userId);

    /**
     * Get all favorites for a user
     */
    java.util.List<Favorite> findByUserId(String userId);

    /**
     * Delete a favorite by user and artwork
     */
    void deleteByUserIdAndArtworkId(String userId, String artworkId);
}
