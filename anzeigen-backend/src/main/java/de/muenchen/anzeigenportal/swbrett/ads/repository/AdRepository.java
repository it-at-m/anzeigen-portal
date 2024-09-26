package de.muenchen.anzeigenportal.swbrett.ads.repository;

import de.muenchen.intranet.sbrett.swbrett.ads.model.Ad;
import de.muenchen.intranet.sbrett.swbrett.ads.model.AdCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface AdRepository extends JpaRepository<Ad, Long>, AdRepositoryCustom {

    List<Ad> findByAdCategory(AdCategory adCategory);

    List<Ad> findByExpiryDateBeforeAndActive(LocalDate localDate, boolean active);
}
