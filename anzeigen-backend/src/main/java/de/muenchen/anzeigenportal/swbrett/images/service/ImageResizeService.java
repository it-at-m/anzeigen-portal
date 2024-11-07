/*
 * Copyright 2020 SWM Services GmbH
 */

package de.muenchen.anzeigenportal.swbrett.images.service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;

@Component
public class ImageResizeService {

    private final static int MAX_SIZE = 500;

    public byte[] resizeImageToPreviewImage(byte[] originalImage) throws IOException {

        // Use https://www.javaxt.com/javaxt-core/io/Image to process EXIF metadata.
        // (The JRE image processing ignores it, and thus the images may turn out rotated.)
        // TODO: above comment still important?

        Image image = Toolkit.getDefaultToolkit().createImage(originalImage);

        // New size to fit inside a MAX_SIZExMAX_SIZE square:
        int newWidth = image.getWidth(null);
        int newHeight = image.getHeight(null);
        if (newWidth > newHeight) {
            newHeight = (newHeight * MAX_SIZE) / newWidth;
            newWidth = MAX_SIZE;
        } else {
            newWidth = (newWidth * MAX_SIZE) / newHeight;
            newHeight = MAX_SIZE;
        }

        // Resize original image to desired preview image size.
        image.getScaledInstance(newWidth, newHeight, Image.SCALE_AREA_AVERAGING);
        BufferedImage outputImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        outputImage.getGraphics().drawImage(image, 0, 0, null);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(outputImage, "jpg", bos);
        return bos.toByteArray();
    }
}
