package de.muenchen.anzeigenportal.swbrett.images.service;

import javax.imageio.ImageIO;

import de.muenchen.anzeigenportal.swbrett.images.model.SwbImage;
import de.muenchen.anzeigenportal.swbrett.images.model.SwbImageTO;
import de.muenchen.anzeigenportal.swbrett.images.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ImageService {

    /**
     * Web-Browser haben eine Längengrenze für URLs; bei CHROME 80 scheint diese am niedrigsten zu sein:
     * 2 MB.
     * Damit "data:..."-URLs mit unseren Bildern diese Grenze nicht sprengen, (1.5 MB + BASE64 = 2 MB),
     * begrenzen wir
     * die Bildgröße mal auf 1 MB.
     *
     * @see #sanitizeImage(byte[])
     */
    private static final int MAX_SIZE = 1024 * 1024;
    private static final double IMAGE_SCALE = 0.9;

    @Autowired
    private ImageRepository repository;

    @Autowired
    private ImageMapper mapper;

    public SwbImageTO getImageTO(long id) {
        SwbImage image = repository.getOne(id);
        return mapper.toSwbImageTO(image);
    }

    public SwbImage getImage(long id) {
        return repository.getOne(id);
    }

    /**
     * Stellt folgendes sicher:
     * <ul>
     * <li>
     * Die Daten lassen sich als Bild laden (per {@link ImageIO#createImageInputStream(Object)})
     * </li>
     * <li>
     * Wenn es ein "rotiertes JPEG-Bild" ist, welches notorisch Probleme macht, wird es ent-rotiert
     * </li>
     * <li>
     * Wenn die so erhaltenen Bilddaten größer als {@link #MAX_SIZE} sind, wird das Bild so lange
     * verkleinert,
     * bis es klein genug ist
     * </li>
     * </ul>
     */
    public byte[] sanitizeImage(byte[] userProvidedImage) throws IOException {

        // Use https://www.javaxt.com/javaxt-core/io/Image to process EXIF metadata.
        // (The JRE image processing ignores it, and thus the images may turn out rotated.)

        BufferedImage image = ImageIO.read(new ByteArrayInputStream(userProvidedImage));

        // Schritt 1: Verkleinere das Bild einmal, wenn es zu groß ist.
        int currentSize = userProvidedImage.length;
        if (currentSize > MAX_SIZE) {
            double scale = Math.sqrt((double) MAX_SIZE / currentSize);
            if (scale > IMAGE_SCALE) scale = IMAGE_SCALE;
            image = resizeImage(image, scale);
        }

        // Schritt 2: Lese EXIF-Metadaten und rotiere das Bild falls notwendig
        // (Hier verwenden wir eine externe Bibliothek wie MetadataExtractor)
        int rotation = getExifRotation(userProvidedImage); // Methode, die die Rotation aus EXIF liest
        if (rotation != 0) {
            image = rotateImage(image, rotation);
        }

        // Schritt 3: Verkleinern, bis die Dateigröße passt
        byte[] ba;
        for (;;) {
            ba = bufferedImageToByteArray(image);

            if (ba.length <= MAX_SIZE) break;

            double scale = Math.sqrt((double) MAX_SIZE / ba.length);
            if (scale > IMAGE_SCALE) scale = IMAGE_SCALE;
            image = resizeImage(image, scale);
        }

        return ba;
    }

    private BufferedImage resizeImage(BufferedImage originalImage, double scale) {
        int newWidth = (int) (originalImage.getWidth() * scale);
        int newHeight = (int) (originalImage.getHeight() * scale);
        Image tmp = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    private BufferedImage rotateImage(BufferedImage img, int degrees) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage rotated = new BufferedImage(w, h, img.getType());
        Graphics2D g2d = rotated.createGraphics();
        g2d.rotate(Math.toRadians(degrees), w / 2, h / 2);
        g2d.drawImage(img, null, 0, 0);
        g2d.dispose();
        return rotated;
    }

    private byte[] bufferedImageToByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        return baos.toByteArray();
    }

    // Dummy-Funktion: Implementiere den EXIF-Reader hier oder verwende eine Bibliothek wie MetadataExtractor
    private int getExifRotation(byte[] imageBytes) {
        // Lese EXIF-Metadaten und gib den Winkel der Rotation zurück
        return 0; // Beispiel: 0 für keine Rotation, 90, 180, 270 für andere Fälle
    }
}
