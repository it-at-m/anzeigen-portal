package de.muenchen.anzeigenportal.swbrett.ads.repository;

import de.muenchen.anzeigenportal.swbrett.ads.model.AdTO;
import de.muenchen.anzeigenportal.swbrett.ads.model.AdType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings({ "PMD.UseObjectWithCaseConventions", "PMD.UseObjectForClearerAPI" })
public interface AdRepositoryCustom {

    Page<AdTO> searchActiveAds(String userId,
            String searchTerm,
            Long categoryId,
            AdType type,
            String sortBy,
            String order,
            Pageable pageable,
            Long adId);

    @PreAuthorize("hasAuthority(T(de.muenchen.anzeigenportal.security.AuthoritiesEnum).FACHADMIN.name())")
    Page<AdTO> searchDeactivatedAds(String userId,
            String searchTerm,
            Long categoryId,
            AdType type,
            String sortBy,
            String order,
            Pageable pageable,
            Long adId);

}
