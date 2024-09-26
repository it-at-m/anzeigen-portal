package de.muenchen.anzeigenportal.swbrett.images.repository;

import de.muenchen.intranet.sbrett.swbrett.images.model.SwbImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<SwbImage, Long>{
}
