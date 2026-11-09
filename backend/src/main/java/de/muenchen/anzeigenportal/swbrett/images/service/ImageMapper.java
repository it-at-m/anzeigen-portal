package de.muenchen.anzeigenportal.swbrett.images.service;

import de.muenchen.anzeigenportal.swbrett.images.model.SwbImage;
import de.muenchen.anzeigenportal.swbrett.images.model.SwbImageTO;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class ImageMapper {

    /**
     * TO ohne originale Bilddatei
     */
    public SwbImageTO toSwbImageTOLight(final SwbImage image) {
        final SwbImageTO fileTO = new SwbImageTO();
        fileTO.setId(image.getId());

        return fileTO;
    }

    public SwbImageTO toSwbImageTO(final SwbImage image) {
        final SwbImageTO fileTO = new SwbImageTO();
        fileTO.setId(image.getId());
        fileTO.setImageBase64(Base64.getEncoder().encodeToString(image.getImage()));

        return fileTO;
    }

    public SwbImage toSwbImage(final SwbImageTO imageTO) {
        final SwbImage file = new SwbImage();
        file.setId(imageTO.getId());
        if (imageTO.getImageBase64() != null) {
            file.setImage(Base64.getDecoder().decode(imageTO.getImageBase64()));
        }

        return file;
    }
}
