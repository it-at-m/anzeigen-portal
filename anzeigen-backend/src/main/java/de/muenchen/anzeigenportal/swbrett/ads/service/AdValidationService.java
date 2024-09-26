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

    public void validate(Ad ad) throws ValidationException {

        if (ad.getImageOriginal() != null && ad.getImageOriginal().hasImage()) {
            Integer maxImgSize = settingService.getSetting(SettingName.MAX_SWB_IMAGE_SIZE).getNumberValue();
            Integer maxImgSizeBytes = maxImgSize * 1024 * 1024;
            if (ad.getImageOriginal().getImageLength() > maxImgSizeBytes) {
                throw new ValidationException("Validierung fehlgeschlagen: Foto darf maximal " + maxImgSize + " MB groß sein!");
            }
        }

        if (ad.getFiles().size() > 0) {
            Integer maxFileSize = settingService.getSetting(SettingName.MAX_SWB_FILE_SIZE).getNumberValue();
            Integer maxFilesLength = settingService.getSetting(SettingName.MAX_SWB_FILES_LENGTH).getNumberValue();

            if (ad.getFiles().size() > (maxFilesLength)) {
                throw new ValidationException("Validierung fehlgeschlagen: Maximal " + maxFilesLength + " Dateien erlaubt!");
            }


            for (SwbFile file : ad.getFiles()) {
                Integer maxFileSizeBytes = maxFileSize * 1024 * 1024;
                if (file.hasFile() && file.getFileLength() > maxFileSizeBytes) {
                    throw new ValidationException("Validierung fehlgeschlagen: Datei darf maximal " + maxFileSize + " MB groß sein!");
                }
            }
        }
    }

}
