package de.muenchen.anzeigenportal.swbrett.images.service;

import de.muenchen.intranet.sbrett.swbrett.images.model.SwbImage;
import de.muenchen.intranet.sbrett.swbrett.images.model.SwbImageTO;
import de.muenchen.intranet.sbrett.swbrett.images.repository.ImageRepository;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

	/**
	 * Web-Browser haben eine Längengrenze für URLs; bei CHROME 80 scheint diese am niedrigsten zu sein: 2 MB.
	 * Damit "data:..."-URLs mit unseren Bildern diese Grenze nicht sprengen, (1.5 MB + BASE64 = 2 MB), begrenzen wir
	 * die Bildgröße mal auf 1 MB.
	 *
	 * @see #sanitizeImage(byte[])
	 */
	private static final int MAX_SIZE = 1024 * 1024;

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
     *   <li>
     *     Die Daten lassen sich als Bild laden (per {@link ImageIO#createImageInputStream(Object)})
     *   </li>
     *   <li>
     *     Wenn es ein "rotiertes JPEG-Bild" ist, welches notorisch Probleme macht, wird es ent-rotiert
     *   </li>
     *   <li>
     *     Wenn die so erhaltenen Bilddaten größer als {@link #MAX_SIZE} sind, wird das Bild so lange verkleinert,
     *     bis es klein genug ist
     *   </li>
     * </ul>
     */
    public byte[] sanitizeImage(byte[] userProvidedImage) {

        // Use https://www.javaxt.com/javaxt-core/io/Image to process EXIF metadata.
        // (The JRE image processing ignores it, and thus the images may turn out rotated.)

    	javaxt.io.Image xtImage = new javaxt.io.Image(userProvidedImage);

        // Verkleinere das Bild *ein mal* vor dem rotieren, denn das Rotieren großer Bilder kann sehr teuer sein.
		int currentSize = userProvidedImage.length;
        if (currentSize > MAX_SIZE) {
        	double scale = Math.sqrt((double) MAX_SIZE / currentSize);
        	if (scale > 0.9) scale = 0.9;
        	xtImage.resize((int) (xtImage.getWidth() * scale), (int) (xtImage.getHeight() * scale));
        }

        // Rotate the image based on the image metadata (EXIF Orientation tag).
        xtImage.rotate();

		byte[] ba;
        for (;;) {

        	// Nun das Bild JPEG-kodieren.
			ba = xtImage.getByteArray();

			// Dateigröße nun klein genug?
        	if (ba.length <= MAX_SIZE) break;

        	// Das Bild so verkleinern, dass die Dateigröße ungefähr passt.
        	double scale = Math.sqrt((double) MAX_SIZE / ba.length);
        	if (scale > 0.9) scale = 0.9;
        	xtImage.resize((int) (xtImage.getWidth() * scale), (int) (xtImage.getHeight() * scale));
        }

        return ba;
    }
}
