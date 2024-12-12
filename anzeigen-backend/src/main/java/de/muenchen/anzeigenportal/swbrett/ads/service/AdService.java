package de.muenchen.anzeigenportal.swbrett.ads.service;

import de.muenchen.anzeigenportal.swbrett.ads.model.Ad;
import de.muenchen.anzeigenportal.swbrett.ads.model.AdCategory;
import de.muenchen.anzeigenportal.swbrett.ads.model.AdTO;
import de.muenchen.anzeigenportal.swbrett.ads.model.AdType;
import de.muenchen.anzeigenportal.swbrett.ads.repository.AdRepository;
import de.muenchen.anzeigenportal.swbrett.files.model.SwbFile;
import de.muenchen.anzeigenportal.swbrett.files.service.FileService;
import de.muenchen.anzeigenportal.swbrett.images.model.SwbImage;
import de.muenchen.anzeigenportal.swbrett.images.service.ImageResizeService;
import de.muenchen.anzeigenportal.swbrett.images.service.ImageService;
import de.muenchen.anzeigenportal.swbrett.settings.model.SettingName;
import de.muenchen.anzeigenportal.swbrett.settings.service.SettingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdService {

    private static final int FIRST_PAGE = 0;

    @Autowired
    private AdRepository repository;

    @Autowired
    private AdMapper mapper;

    @Autowired
    private ImageResizeService imageResizeService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private FileService fileService;

    @Autowired
    private SettingService settingService;

    @Autowired
    private AdValidationService validationService;

    @SuppressWarnings({ "PMD.UseObjectWithCaseConventions", "PMD.UseObjectForClearerAPI" })
    public Page<AdTO> findAds(final String userId, final String searchTerm, final Long categoryId, final AdType type, final String sortBy, final String order,
            final Integer page, final Long adId,
            final boolean isActive) {
        String interrnalSortBy = sortBy;
        String internalOrder = order;
        Integer internalPage = page;

        if (interrnalSortBy == null) {
            interrnalSortBy = settingService.getSetting(SettingName.DEFAULT_SORTING).getTextValue();
        }
        if (internalOrder == null) {
            internalOrder = settingService.getSetting(SettingName.DEFAULT_ORDERING).getTextValue();
        }
        if (internalPage == null) {
            internalPage = FIRST_PAGE;
        }
        final Pageable pageable = PageRequest.of(internalPage, settingService.getSetting(SettingName.MAX_PAGE_SIZE).getNumberValue());
        if (isActive) {
            return repository.searchActiveAds(userId, searchTerm, categoryId, type, interrnalSortBy, internalOrder, pageable, adId);
        } else {
            return repository.searchDeactivatedAds(userId, searchTerm, categoryId, type, interrnalSortBy, internalOrder, pageable, adId);
        }
    }

    public AdTO getAd(final long id) {
        final Ad ad = repository.getOne(id);
        return mapper.toAdTO(ad);
    }

    public void incrementView(final long id) {
        final Ad ad = repository.getOne(id);
        ad.setViews(ad.getViews() + 1);
        repository.save(ad);
    }

    public AdTO createAd(final AdTO adTO) throws IOException {
        if (adTO.getCreationDateTime() == null) {
            adTO.setCreationDateTime(LocalDateTime.now());
        }
        if (adTO.getExpiryDate() == null) {
            final Integer setting = this.settingService.getSetting(SettingName.MAX_EXPIRY_DATE_RANGE).getNumberValue();
            adTO.setExpiryDate(LocalDate.now().plusWeeks(setting));
        }

        final Ad ad = mapper.toAd(adTO);
        validationService.validate(ad);

        if (ad.getImageOriginal() != null) {
            final byte[] imagePreview = imageResizeService.resizeImageToPreviewImage(ad.getImageOriginal().getImage());
            ad.setImagePreview(imagePreview);
        }

        final Ad savedAd = repository.save(ad);
        return mapper.toAdTO(savedAd);
    }

    public AdTO updateAd(final long id, final AdTO updatedAdTO, HttpServletRequest request) throws IOException {
        final Ad updatedAd = mapper.toAd(updatedAdTO);
        validationService.validate(updatedAd);

        if (updatedAd.getImageOriginal() != null) {
            if (updatedAd.getImageOriginal().hasImage()) {
                final byte[] imagePreview = imageResizeService.resizeImageToPreviewImage(updatedAd.getImageOriginal().getImage());
                updatedAd.setImagePreview(imagePreview);
            } else {
                final SwbImage image = imageService.getImage(updatedAd.getImageOriginal().getId());
                updatedAd.setImageOriginal(image);
            }

        }

        final List<SwbFile> updatedFiles = updatedAd.getFiles().stream()
                .map(swbFile -> swbFile.hasFile() ? swbFile : fileService.getFile(swbFile.getId()))
                .collect(Collectors.toList());

        updatedAd.setFiles(updatedFiles);

        final Ad ad = repository.getOne(id);
        updatedAd.setId(ad.getId());

        return mapper.toAdTO(repository.save(updatedAd));
    }

    public void deactivateAd(final long id, HttpServletRequest request) {
        final Ad ad = repository.getOne(id);
        ad.setActive(false);
        repository.save(ad);
    }

    @PreAuthorize("hasAuthority(T(de.muenchen.anzeigenportal.security.AuthoritiesEnum).REFARCH_BACKEND_DELETE_THEENTITY.name())")
    public void deleteAd(final long id, HttpServletRequest request) {
        final Ad ad = repository.getOne(id);
        repository.delete(ad);
    }

    @PreAuthorize("hasAuthority(T(de.muenchen.anzeigenportal.security.AuthoritiesEnum).REFARCH_BACKEND_WRITE_THEENTITY.name())")
    public void updateAllCategories(final AdCategory oldCat, final AdCategory newCat) {
        final List<Ad> allAdsOfCategory = repository.findByAdCategory(oldCat);

        allAdsOfCategory.stream().forEach(ad -> {
            ad.setAdCategory(newCat);
            repository.save(ad);
        });
    }
}
