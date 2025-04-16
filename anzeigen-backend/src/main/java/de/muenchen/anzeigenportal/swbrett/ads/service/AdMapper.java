package de.muenchen.anzeigenportal.swbrett.ads.service;

import de.muenchen.anzeigenportal.swbrett.ads.model.Ad;
import de.muenchen.anzeigenportal.swbrett.ads.model.AdTO;
import de.muenchen.anzeigenportal.swbrett.files.model.SwbFile;
import de.muenchen.anzeigenportal.swbrett.files.model.SwbFileTO;
import de.muenchen.anzeigenportal.swbrett.files.service.FileMapper;
import de.muenchen.anzeigenportal.swbrett.images.model.SwbImage;
import de.muenchen.anzeigenportal.swbrett.images.model.SwbImageTO;
import de.muenchen.anzeigenportal.swbrett.images.service.ImageMapper;
import de.muenchen.anzeigenportal.swbrett.users.service.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdMapper {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private FileMapper fileMapper;

    public Ad toAd(final AdTO adTO) {
        final Ad ad = new Ad();
        ad.setId(adTO.getId());
        ad.setSwbUser(userMapper.toSwbUser(adTO.getSwbUser()));
        ad.setAdCategory(adTO.getAdCategory());
        ad.setAdType(adTO.getAdType());
        ad.setActive(adTO.isActive());
        ad.setTitle(adTO.getTitle());
        ad.setDescription(adTO.getDescription());
        ad.setPrice(adTO.getPrice());
        ad.setPhone(adTO.getPhone());
        ad.setEmail(adTO.getEmail());
        ad.setLink(adTO.getLink());
        ad.setCreationDateTime(adTO.getCreationDateTime());
        ad.setExpiryDate(adTO.getExpiryDate());
        ad.setViews(adTO.getViews());
        ad.setRentalDate(adTO.getRentalDate());

        // map Base64 to byte[]
        if (adTO.getImagePreviewBase64() != null) {
            final String imagePreviewBase64 = adTO.getImagePreviewBase64();
            ad.setImagePreview(Base64.getDecoder().decode(imagePreviewBase64));
        }

        if (adTO.getAdImg() != null) {
            final SwbImage swbImage = imageMapper.toSwbImage(adTO.getAdImg());
            ad.setImageOriginal(swbImage);
        }

        if (!adTO.getAdFiles().isEmpty()) {
            final List<SwbFile> swbFiles = adTO.getAdFiles().stream()
                    .map(fileMapper::toSwbFile)
                    .collect(Collectors.toList());
            ad.setFiles(swbFiles);
        }

        return ad;
    }

    public AdTO toAdTO(final Ad ad) {
        final AdTO adTO = new AdTO();
        adTO.setId(ad.getId());
        adTO.setSwbUser(userMapper.toSwbUserTOAnonym(ad.getSwbUser()));
        adTO.setAdCategory(ad.getAdCategory());
        adTO.setAdType(ad.getAdType());
        adTO.setActive(ad.isActive());
        adTO.setTitle(ad.getTitle());
        adTO.setDescription(ad.getDescription());
        adTO.setPrice(ad.getPrice());
        adTO.setPhone(ad.getPhone());
        adTO.setEmail(ad.getEmail());
        adTO.setLink(ad.getLink());
        adTO.setCreationDateTime(ad.getCreationDateTime());
        adTO.setExpiryDate(ad.getExpiryDate());
        adTO.setViews(ad.getViews());
        adTO.setRentalDate(ad.getRentalDate());

        // map byte[] to Base64
        if (ad.getImagePreview() != null) {
            final byte[] imagePreview = ad.getImagePreview();
            adTO.setImagePreviewBase64(Base64.getEncoder().encodeToString(imagePreview));
        }

        if (ad.getImageOriginal() != null) {
            final SwbImageTO swbImageTO = imageMapper.toSwbImageTOLight(ad.getImageOriginal());
            adTO.setAdImg(swbImageTO);
        }

        if (!ad.getFiles().isEmpty()) {
            final List<SwbFileTO> adFiles = ad.getFiles().stream()
                    .map(fileMapper::toSwbFileTOLight)
                    .collect(Collectors.toList());
            adTO.setAdFiles(adFiles);
        }

        return adTO;
    }

}
