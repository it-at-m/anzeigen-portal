package de.muenchen.anzeigenportal.swbrett.images.model;

/**
 * Transferobject für Bilder.
 */
public class SwbImageTO {

    private long id;

    private String imageBase64;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
