package de.muenchen.anzeigenportal.swbrett.ads.service;

import de.muenchen.intranet.sbrett.swbrett.ads.model.Ad;
import de.muenchen.intranet.sbrett.swbrett.ads.model.AdCategory;
import de.muenchen.intranet.sbrett.swbrett.ads.model.AdTO;
import de.muenchen.intranet.sbrett.swbrett.ads.model.AdType;
import de.muenchen.intranet.sbrett.swbrett.ads.repository.AdRepository;
import de.muenchen.intranet.sbrett.swbrett.files.model.SwbFile;
import de.muenchen.intranet.sbrett.swbrett.files.service.FileService;
import de.muenchen.intranet.sbrett.swbrett.images.model.SwbImage;
import de.muenchen.intranet.sbrett.swbrett.images.service.ImageResizeService;
import de.muenchen.intranet.sbrett.swbrett.images.service.ImageService;
import de.muenchen.intranet.sbrett.swbrett.settings.model.SettingName;
import de.muenchen.intranet.sbrett.swbrett.settings.service.SettingService;
import de.muenchen.intranet.sbrett.swbrett.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdService {

    private final int FIRST_PAGE = 0;

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

    public Page<AdTO> findAds(String userId, String searchTerm, Long categoryId, AdType type, String sortBy, String order, Integer page, Long adId, boolean isActive) {
        if (sortBy == null) {
            sortBy = settingService.getSetting(SettingName.DEFAULT_SORTING).getTextValue();
        }
        if (order == null) {
            order = settingService.getSetting(SettingName.DEFAULT_ORDERING).getTextValue();;
        }
        if (page == null) {
            page = FIRST_PAGE;
        }
        Pageable pageable = PageRequest.of(page, settingService.getSetting(SettingName.MAX_PAGE_SIZE).getNumberValue());
        if (isActive) {
            return repository.searchActiveAds(userId, searchTerm, categoryId, type, sortBy, order, pageable, adId);
        } else {
            return repository.searchDeactivatedAds(userId, searchTerm, categoryId, type, sortBy, order, pageable, adId);
        }
    }

    public AdTO getAd(long id) {
        Ad ad = repository.getOne(id);
        return mapper.toAdTO(ad);
    }

    public void incrementView(long id) {
        Ad ad = repository.getOne(id);
        ad.setViews(ad.getViews() + 1);
        repository.save(ad);
    }

    public AdTO createAd(AdTO adTO) throws IOException, ValidationException {
        if (adTO.getCreationDateTime() == null) {
            adTO.setCreationDateTime(LocalDateTime.now());
        }
        if (adTO.getExpiryDate() == null) {
            Integer setting = this.settingService.getSetting(SettingName.MAX_EXPIRY_DATE_RANGE).getNumberValue();
            adTO.setExpiryDate(LocalDate.now().plusWeeks(setting));
        }

        Ad ad = mapper.toAd(adTO);
        validationService.validate(ad);

        if (ad.getImageOriginal() != null) {
            byte[] imagePreview = imageResizeService.resizeImageToPreviewImage(ad.getImageOriginal().getImage());
            ad.setImagePreview(imagePreview);
        }

        Ad savedAd = repository.save(ad);
        return mapper.toAdTO(savedAd);
    }

    public AdTO updateAd(long id, AdTO updatedAdTO, HttpServletRequest request) throws IOException, ValidationException {
        Ad updatedAd = mapper.toAd(updatedAdTO);
        validationService.validate(updatedAd);

        if (updatedAd.getImageOriginal() != null) {
            if (updatedAd.getImageOriginal().hasImage()) {
                byte[] imagePreview = imageResizeService.resizeImageToPreviewImage(updatedAd.getImageOriginal().getImage());
                updatedAd.setImagePreview(imagePreview);
            } else {
                SwbImage image = imageService.getImage(updatedAd.getImageOriginal().getId());
                updatedAd.setImageOriginal(image);
            }

        }

        List<SwbFile> updatedFiles = updatedAd.getFiles().stream()
                .map(swbFile -> swbFile.hasFile() ? swbFile : fileService.getFile(swbFile.getId()))
                .collect(Collectors.toList());

        updatedAd.setFiles(updatedFiles);

        Ad ad = repository.getOne(id);
        updatedAd.setId(ad.getId());

        return mapper.toAdTO(repository.save(updatedAd));
    }

    public void deactivateAd(long id, HttpServletRequest request) {
        Ad ad = repository.getOne(id);
        ad.setActive(false);
        repository.save(ad);
    }

    @PreAuthorize("hasAuthority(T(de.muenchen.intranet.sbrett.security.AuthoritiesEnum).BACKEND_DELETE_THEENTITY.name())")
    public void deleteAd(long id, HttpServletRequest request) {
        Ad ad = repository.getOne(id);
        repository.delete(ad);
    }

    @PreAuthorize("hasAuthority(T(de.muenchen.intranet.sbrett.security.AuthoritiesEnum).BACKEND_WRITE_THEENTITY.name())")
    public void updateAllCategories(AdCategory oldCat, AdCategory newCat) {
        List<Ad> allAdsOfCategory = repository.findByAdCategory(oldCat);

        allAdsOfCategory.stream().forEach(ad -> {
            ad.setAdCategory(newCat);
            repository.save(ad);
        });
    }
}
