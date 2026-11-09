package de.muenchen.anzeigenportal.swbrett.images.service;

import javax.imageio.ImageIO;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import de.muenchen.anzeigenportal.swbrett.images.model.SwbImage;
import de.muenchen.anzeigenportal.swbrett.images.model.SwbImageTO;
import de.muenchen.anzeigenportal.swbrett.images.repository.ImageRepository;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Service
@SuppressFBWarnings("EI_EXPOSE_REP")
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

    @Transactional
    public SwbImageTO getImageTO(final long id) {
        final SwbImage image = repository.getOne(id);
        return mapper.toSwbImageTO(image);
    }

    @Transactional
    public SwbImage getImage(final long id) {
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
    public byte[] sanitizeImage(final byte[] userProvidedImage) throws IOException, ImageProcessingException, MetadataException {

        BufferedImage image = ImageIO.read(new ByteArrayInputStream(userProvidedImage));
        if (image == null) {
            throw new IOException("Could not read image data");
        }

        final int currentSize = userProvidedImage.length;
        if (currentSize > MAX_SIZE) {
            double scale = Math.sqrt((double) MAX_SIZE / currentSize);
            if (scale > IMAGE_SCALE) {
                scale = IMAGE_SCALE;
            }

            int newWidth = (int) (image.getWidth() * scale);

            BufferedImage oldImage = image;
            image = Scalr.resize(image, Scalr.Method.SPEED, Scalr.Mode.FIT_EXACT, newWidth);
            oldImage.flush();
        }

        final int rotation = getExifRotation(userProvidedImage);
        if (rotation != 0) {
            BufferedImage oldImage = image;
            image = rotateImageWithScalr(image, rotation);
            oldImage.flush();
        }

        byte[] ba = null;
        int maxIterations = 5;
        int iteration = 0;

        while (iteration < maxIterations) {
            ba = bufferedImageToJpgByteArray(image);

            if (ba.length <= MAX_SIZE) {
                break;
            }

            // Skalierungsfaktor berechnen
            double scale = Math.sqrt((double) MAX_SIZE / ba.length);
            if (scale > IMAGE_SCALE) {
                scale = IMAGE_SCALE;
            }

            int newWidth = (int) (image.getWidth() * scale);
            if (newWidth <= 10) break;

            BufferedImage oldImage = image;
            image = Scalr.resize(image, Scalr.Method.SPEED, Scalr.Mode.FIT_EXACT, newWidth);
            oldImage.flush();

            iteration++;
        }

        if (image != null) {
            image.flush();
        }

        return ba;
    }

    /**
     * Ersetzt die alte rotateImage-Methode mit AffineTransform.
     * Nutzt stattdessen die robuste Scalr-Bibliothek.
     */
    private BufferedImage rotateImageWithScalr(final BufferedImage img, final int degrees) {
        return switch (degrees) {
            case 90 -> Scalr.rotate(img, Scalr.Rotation.CW_90);
            case 180 -> Scalr.rotate(img, Scalr.Rotation.CW_180);
            case 270 -> Scalr.rotate(img, Scalr.Rotation.CW_270);
            default -> img; // Falls Winkel unerwartet, Bild unverändert zurückgeben
        };
    }

    /**
     * Ersetzt die alte bufferedImageToByteArray-Methode.
     * Speichert zwingend als JPG und löst Probleme mit transparenten PNGs (Alphakanal).
     */
    private byte[] bufferedImageToJpgByteArray(final BufferedImage image) throws IOException {
        // TYPE_INT_RGB entfernt den Transparenzkanal.
        final BufferedImage convertedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        final Graphics2D g2d = convertedImage.createGraphics();

        // WICHTIG: Zeichne einen weißen Hintergrund.
        // Wenn du ein transparentes PNG in ein JPG wandelst, wird der Hintergrund sonst oft eklig pink oder schwarz.
        g2d.drawImage(image, 0, 0, Color.WHITE, null);
        g2d.dispose();

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(convertedImage, "jpg", baos); // Als JPG speichern!

        convertedImage.flush(); // Speichermanagement

        return baos.toByteArray();
    }

    private int getExifRotation(final byte[] imageBytes) throws ImageProcessingException, IOException, MetadataException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
        final Metadata metadata = ImageMetadataReader.readMetadata(bais);
        final ExifIFD0Directory dir = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
        if (dir != null && dir.containsTag(ExifIFD0Directory.TAG_ORIENTATION)) {
            final int orientation = dir.getInt(ExifIFD0Directory.TAG_ORIENTATION);
            return switch (orientation) {
            case 3 -> 180;
            case 6 -> 90;
            case 8 -> 270;
            default -> 0;
            };
        }
        return 0;
    }
}
