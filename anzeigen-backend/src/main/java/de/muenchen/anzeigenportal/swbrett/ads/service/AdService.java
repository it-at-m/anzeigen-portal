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
import de.muenchen.anzeigenportal.swbrett.settings.model.SettingTO;
import de.muenchen.anzeigenportal.swbrett.settings.service.SettingService;
import de.muenchen.anzeigenportal.swbrett.users.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdService {

    private static final int FIRST_PAGE = 0;

    private static final String AD_NOT_FOUND = "Ad not found";

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

    @Autowired
    private UserService userService;

    @SuppressWarnings({ "PMD.UseObjectWithCaseConventions", "PMD.UseObjectForClearerAPI" })
    public Page<AdTO> findAds(final String userId, final String searchTerm, final Long categoryId, final String types, final String sortBy, final String order,
            final Integer page, final Long adId,
            final boolean isActive) {
        String interrnalSortBy = sortBy;
        String internalOrder = order;
        Integer internalPage = page;

        // Not proud of this conversion - workaround for https://github.com/it-at-m/refarch/blob/00be5e8a4462ed93f8d32a6593a0d162d24da865/refarch-gateway/src/main/java/de/muenchen/refarch/gateway/filter/GlobalRequestParameterPollutionFilter.java
        List<AdType> typeList = new ArrayList<>();
        if (types != null) {
            typeList = Arrays.stream(types.split(",")).map(type -> AdType.valueOf(type.toUpperCase(Locale.GERMAN))).toList();
        }

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
            return repository.searchActiveAds(userId, searchTerm, categoryId, typeList, interrnalSortBy, internalOrder, pageable, adId);
        } else {
            return repository.searchDeactivatedAds(userId, searchTerm, categoryId, typeList, interrnalSortBy, internalOrder, pageable, adId);
        }
    }

    public AdTO getAd(final long id) {
        final Ad ad = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ad not found"));
        return mapper.toAdTO(ad);
    }

    public void incrementView(final long id) {
        final Ad ad = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, AD_NOT_FOUND));
        ad.setViews(ad.getViews() + 1);
        repository.save(ad);
    }

    @SuppressWarnings("PMD.CyclomaticComplexity")
    public AdTO createAd(final AdTO adTO) throws IOException {
        if (adTO.getEmail() != null && isEMailDomainDisallowed(adTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "EMail not allowed");
        }

        if (adTO.getCreationDateTime() == null) {
            adTO.setCreationDateTime(LocalDateTime.now());
        }
        if (adTO.getAdType() == AdType.RENTAL && adTO.getRentalDate() == null) {
            final Integer setting = this.settingService.getSetting(SettingName.MAX_RENTAL_DATE_RANGE).getNumberValue();
            adTO.setExpiryDate(LocalDate.now().plusWeeks(setting));
        }
        if (adTO.getExpiryDate() == null) {
            final Integer setting = this.settingService.getSetting(SettingName.MAX_EXPIRY_DATE_RANGE).getNumberValue();
            adTO.setExpiryDate(LocalDate.now().plusWeeks(setting));
        }

        // Sanitize description of any scripts or styles
        adTO.setDescription(HtmlSanitizerUtil.sanitize(adTO.getDescription()));

        final Ad ad = mapper.toAd(adTO);
        validationService.validate(ad);

        if (ad.getImageOriginal() != null && ad.getImageOriginal().hasImage()) {
            final byte[] imagePreview = imageResizeService.resizeImageToPreviewImage(ad.getImageOriginal().getImage());
            ad.setImagePreview(imagePreview);
        }

        final Ad savedAd = repository.save(ad);
        return mapper.toAdTO(savedAd);
    }

    public AdTO updateAd(final long id, final AdTO updatedAdTO, HttpServletRequest request) throws IOException {
        final Ad ad = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, AD_NOT_FOUND));

        if (!userService.isCurrentUser(ad.getSwbUser().getId()) && !userService.currentUserIsAdmin()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
        }

        if (updatedAdTO.getEmail() != null && isEMailDomainDisallowed(updatedAdTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "EMail not allowed");
        }

        // Sanitize description of any scripts or styles
        updatedAdTO.setDescription(HtmlSanitizerUtil.sanitize(updatedAdTO.getDescription()));

        final Ad updatedAd = mapper.toAd(updatedAdTO);
        validationService.validate(updatedAd);

        if (updatedAd.getImageOriginal() != null) {
            if (updatedAd.getImageOriginal().hasImage()) {
                final byte[] imagePreview = imageResizeService.resizeImageToPreviewImage(updatedAd.getImageOriginal().getImage());
                updatedAd.setImagePreview(imagePreview);
            } else {
                // the obj is not null and no picture was included - therefore the id must be set. Load the picture which is already saved
                final SwbImage image = imageService.getImage(updatedAd.getImageOriginal().getId());
                updatedAd.setImageOriginal(image);
            }
        }

        final List<SwbFile> updatedFiles = updatedAd.getFiles().stream()
                .map(swbFile -> swbFile.hasFile() ? swbFile : fileService.getFile(swbFile.getId()))
                .collect(Collectors.toList());

        updatedAd.setFiles(updatedFiles);
        updatedAd.setId(ad.getId());

        return mapper.toAdTO(repository.save(updatedAd));
    }

    public void deactivateAd(final long id, HttpServletRequest request) {
        final Ad ad = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, AD_NOT_FOUND));

        if (!userService.isCurrentUser(ad.getSwbUser().getId()) && !userService.currentUserIsAdmin()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
        }

        ad.setActive(false);
        repository.save(ad);
    }

    @PreAuthorize("hasAuthority(T(de.muenchen.anzeigenportal.security.AuthoritiesEnum).fachadmin.name())")
    public void deleteAd(final long id, HttpServletRequest request) {
        final Ad ad = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, AD_NOT_FOUND));
        repository.delete(ad);
    }

    @PreAuthorize("hasAuthority(T(de.muenchen.anzeigenportal.security.AuthoritiesEnum).fachadmin.name())")
    public void updateAllCategories(final AdCategory oldCat, final AdCategory newCat) {
        final List<Ad> allAdsOfCategory = repository.findByAdCategory(oldCat);

        allAdsOfCategory.forEach(ad -> {
            ad.setAdCategory(newCat);
            repository.save(ad);
        });
    }

    public boolean isEMailDomainDisallowed(final String emailToCheck) {
        final SettingTO emailSettings = settingService.getSetting(SettingName.EMAIL_DOMAIN_LIST);
        final String emailDomainList = emailSettings.getTextValue();

        if (emailSettings.getFlagValue() == null || emailDomainList.isEmpty()) {
            return false;
        }

        final String emailDomainToCheck = emailToCheck.substring(emailToCheck.indexOf('@') + 1).toLowerCase(Locale.GERMAN);

        return emailSettings.getFlagValue() ^ Arrays.stream(emailDomainList.split(","))
                .anyMatch(disallowedEmailDomain -> emailDomainToCheck.equals(disallowedEmailDomain.toLowerCase(Locale.GERMAN)));
    }

}
