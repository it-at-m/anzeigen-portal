/*
 * Copyright 2020 SWM Services GmbH
 */

package de.muenchen.anzeigenportal.swbrett.images.service;

import java.io.IOException;

import org.springframework.stereotype.Component;


@Component
public class ImageResizeService {

    final static int MAX_SIZE = 500;

    public byte[] resizeImageToPreviewImage(byte[] originalImage) throws IOException {

        // Use https://www.javaxt.com/javaxt-core/io/Image to process EXIF metadata.
        // (The JRE image processing ignores it, and thus the images may turn out rotated.)

    	javaxt.io.Image xtImage = new javaxt.io.Image(originalImage);

        // Rotate the image based on the image metadata (EXIF Orientation tag).
        xtImage.rotate();

        // New size to fit inside a MAX_SIZExMAX_SIZE square:
        int w = xtImage.getWidth();
        int h = xtImage.getHeight();
        if (w > h) {
        	h = (h * MAX_SIZE) / w;
        	w = MAX_SIZE;
        } else {
        	w = (w * MAX_SIZE) / h;
        	h = MAX_SIZE;
        }

        // Resize original image to desired preview image size.
        xtImage.resize(w, h);

        return xtImage.getByteArray();
    }
}
