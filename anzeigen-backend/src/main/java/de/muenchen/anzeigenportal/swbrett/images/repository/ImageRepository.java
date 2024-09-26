package de.muenchen.anzeigenportal.swbrett.images.repository;

import de.muenchen.anzeigenportal.swbrett.images.model.SwbImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<SwbImage, Long>{
}
