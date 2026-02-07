package com.unveil.repository;

import com.unveil.model.ArtworkView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtworkViewRepository extends JpaRepository<ArtworkView, Long> {

    /**
     * Find all views for a specific user, ordered by creation time descending
     */
    List<ArtworkView> findByUserIdOrderByCreatedAtDesc(String userId);

    /**
     * Find all views for a specific user with pagination
     */
    Page<ArtworkView> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);

    /**
     * Count total views for a user
     */
    long countByUserId(String userId);

    /**
     * Get the most recently viewed artworks for a user
     */
    @Query("SELECT av FROM ArtworkView av WHERE av.userId = :userId ORDER BY av.createdAt DESC")
    List<ArtworkView> findRecentViewsByUserId(@Param("userId") String userId, Pageable pageable);
}

