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

    public AdCategory getAdCategory(final long id) {
        return repository.getOne(id);
    }

    public AdCategory createAdCategory(final AdCategory adCategory) {
        return repository.save(adCategory);
    }

    @PreAuthorize("hasAuthority(T(de.muenchen.anzeigenportal.security.AuthoritiesEnum).FACHADMIN.name())")
    public AdCategory saveAdCategory(final AdCategory adCategory) {
        if (adCategory.isStandard()) {
            getAdCategories().stream().forEach(cat -> {
                cat.setStandard(false);
                repository.save(cat);
            });
        }
        return repository.save(adCategory);
    }

    @PreAuthorize("hasAuthority(T(de.muenchen.anzeigenportal.security.AuthoritiesEnum).FACHADMIN.name())")
    public void deleteAdCategory(final long id) {
        final AdCategory category = repository.getOne(id);
        final AdCategory standardCat = repository.findByStandardTrue();

        if (category.isStandard()) {
            throw new IllegalArgumentException("This category is standard and cannot be deleted.");
        }

        adService.updateAllCategories(category, standardCat);

        repository.delete(category);
    }
}
