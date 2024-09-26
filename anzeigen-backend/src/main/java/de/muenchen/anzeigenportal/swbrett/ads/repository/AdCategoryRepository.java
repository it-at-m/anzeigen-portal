package de.muenchen.anzeigenportal.swbrett.ads.repository;

import de.muenchen.intranet.sbrett.swbrett.ads.model.AdCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdCategoryRepository extends JpaRepository<AdCategory, Long> {
    AdCategory findByStandardTrue();
}
