package de.muenchen.anzeigenportal.swbrett.ads.service;

import de.muenchen.anzeigenportal.swbrett.ads.model.AdCategory;
import de.muenchen.anzeigenportal.swbrett.ads.repository.AdCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdCategoryService {

    @Autowired
    private AdCategoryRepository repository;

    @Autowired
    private AdService adService;

    public List<AdCategory> getAdCategories() {
        return repository.findAll();
    }

    public AdCategory getAdCategory(long id) {
        return repository.getOne(id);
    }

    public AdCategory createAdCategory(AdCategory adCategory) {
        return repository.save(adCategory);
    }

    @PreAuthorize("hasAuthority(T(de.muenchen.intranet.sbrett.security.AuthoritiesEnum).BACKEND_WRITE_THEENTITY.name())")
    public AdCategory saveAdCategory(AdCategory adCategory) {
        if (adCategory.isStandard()) {
            getAdCategories().stream().forEach(cat -> {
                cat.setStandard(false);
                repository.save(cat);
            });
        }
        return repository.save(adCategory);
    }

    @PreAuthorize("hasAuthority(T(de.muenchen.intranet.sbrett.security.AuthoritiesEnum).BACKEND_DELETE_THEENTITY.name())")
    public void deleteAdCategory(long id) {
        AdCategory category = repository.getOne(id);
        AdCategory standardCat = repository.findByStandardTrue();

        if (category.isStandard()) {
            // TODO throw error // TODO: yeah ... nothing is happening here thoo
        }

        adService.updateAllCategories(category, standardCat);

        repository.delete(category);
    }
}
