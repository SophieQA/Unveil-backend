package com.unveil.service;

import com.unveil.dto.GalleryLocationDto;
import com.unveil.model.Gallery;
import com.unveil.repository.GalleryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GalleryService {

    private final GalleryRepository galleryRepository;

    public GalleryService(GalleryRepository galleryRepository) {
        this.galleryRepository = galleryRepository;
    }

    public Optional<GalleryLocationDto> getGalleryLocation(String galleryNumber) {
        return galleryRepository.findByGalleryNumber(galleryNumber)
                .map(this::convertToDto);
    }

    public List<GalleryLocationDto> getGalleriesByFloor(String floor) {
        return galleryRepository.findByFloor(floor)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private GalleryLocationDto convertToDto(Gallery gallery) {
        return GalleryLocationDto.builder()
                .galleryNumber(gallery.getGalleryNumber())
                .galleryName(gallery.getGalleryName())
                .floor(gallery.getFloor())
                .xCoordinate(gallery.getXCoordinate() != null ? gallery.getXCoordinate().doubleValue() : null)
                .yCoordinate(gallery.getYCoordinate() != null ? gallery.getYCoordinate().doubleValue() : null)
                .polygonData(gallery.getPolygonData())
                .build();
    }
}
