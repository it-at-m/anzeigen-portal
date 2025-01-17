/*
 * Copyright 2020 SWM Services GmbH
 */

package de.muenchen.anzeigenportal.swbrett.images.service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;

@Slf4j
@Component
@SuppressWarnings("PMD.UselessParentheses")
public class ImageResizeService {

    private final static int MAX_SIZE = 500;

    public byte[] resizeImageToPreviewImage(final byte[] originalImage) throws IOException {

        BufferedImage bufferedImage = createImageFromBytes(originalImage);
        if (bufferedImage == null) {
            log.debug("No image to resize.");
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "This media type is not supported.");
        }

        BufferedImage resizedImage = Scalr.resize(bufferedImage, MAX_SIZE);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "jpg", baos);
        return baos.toByteArray();
    }

    private BufferedImage createImageFromBytes(byte[] imageData) {
        log.debug("Creating image from byte[] with length: {}", imageData.length);
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        try {
            return ImageIO.read(bais);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
