package com.unveil.repository;

import com.unveil.model.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    Optional<Gallery> findByGalleryNumber(String galleryNumber);
    List<Gallery> findByFloor(String floor);
}
