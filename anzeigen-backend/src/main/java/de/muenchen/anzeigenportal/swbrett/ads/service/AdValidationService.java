package de.muenchen.anzeigenportal.swbrett.ads.service;

import de.muenchen.anzeigenportal.swbrett.ads.model.Ad;
import de.muenchen.anzeigenportal.swbrett.files.model.SwbFile;
import de.muenchen.anzeigenportal.swbrett.settings.model.SettingName;
import de.muenchen.anzeigenportal.swbrett.settings.service.SettingService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdValidationService {

    @Autowired
    private SettingService settingService;

    public void validate(final Ad ad) {
        validateImage(ad);

        validateFiles(ad);
    }

    private void validateImage(final Ad ad) {
        if (ad.getImageOriginal() != null && ad.getImageOriginal().hasImage()) {
            final int maxImgSizeBytes = getMaxImageSizeInBytes();
            if (ad.getImageOriginal().getImageLength() > maxImgSizeBytes) {
                throw new ValidationException("Validierung fehlgeschlagen: Foto darf maximal " + (maxImgSizeBytes / (1024 * 1024)) + " MB groß sein!");
            }
        }
    }

    private void validateFiles(final Ad ad) {
        if (!ad.getFiles().isEmpty()) {
            final int maxFilesLength = getMaxFilesLength();

            if (ad.getFiles().size() > maxFilesLength) {
                throw new ValidationException("Validierung fehlgeschlagen: Maximal " + maxFilesLength + " Dateien erlaubt!");
            }

            for (final SwbFile file : ad.getFiles()) {
                validateFile(file);
            }
        }
    }

    private void validateFile(final SwbFile file) {
        final int maxFileSizeBytes = getMaxFileSizeInBytes();

        if (file.hasFile() && file.getFileLength() > maxFileSizeBytes) {
            throw new ValidationException("Validierung fehlgeschlagen: Datei darf maximal " + (maxFileSizeBytes / (1024 * 1024)) + " MB groß sein!");
        }
    }

    private int getMaxImageSizeInBytes() {
        final Integer maxImgSize = settingService.getSetting(SettingName.MAX_SWB_IMAGE_SIZE).getNumberValue();
        return maxImgSize * 1024 * 1024;
    }

    private int getMaxFileSizeInBytes() {
        final Integer maxFileSize = settingService.getSetting(SettingName.MAX_SWB_FILE_SIZE).getNumberValue();
        return maxFileSize * 1024 * 1024;
    }

    private int getMaxFilesLength() {
        return settingService.getSetting(SettingName.MAX_SWB_FILES_LENGTH).getNumberValue();
    }

}
