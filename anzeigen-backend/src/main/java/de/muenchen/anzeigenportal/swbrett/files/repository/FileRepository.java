package de.muenchen.anzeigenportal.swbrett.files.repository;

import de.muenchen.anzeigenportal.swbrett.files.model.SwbFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<SwbFile, Long> {
}
