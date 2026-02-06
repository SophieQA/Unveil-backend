package com.unveil.repository;

import com.unveil.model.Artwork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {

    /**
     * Find artwork by artworkId
     */
    Optional<Artwork> findByArtworkId(String artworkId);

    /**
     * Search artworks by title or artist with pagination
     */
    @Query("SELECT a FROM Artwork a WHERE " +
           "LOWER(a.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(a.artist) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Artwork> searchByQuery(@Param("query") String query, Pageable pageable);

    /**
     * Get all artworks with pagination
     */
    Page<Artwork> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
